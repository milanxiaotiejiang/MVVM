package com.huaqing.property.ui.login

import arrow.core.Either
import com.huaqing.property.base.repository.BaseRepositoryBoth
import com.huaqing.property.base.repository.ILocalDataSource
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService
import com.huaqing.property.common.helper.PrefsHelper
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.manager.UserManager
import com.huaqing.property.db.AppDatabase
import com.huaqing.property.db.UserInfo
import com.huaqing.property.model.Errors
import io.reactivex.Flowable
import io.reactivex.Single

class LoginDataSourceRepository(
    remote: ILoginRemoteDataSource,
    local: ILoginLocalDataSource
) : BaseRepositoryBoth<ILoginRemoteDataSource, ILoginLocalDataSource>(remote, local) {

    fun login(telephone: String, password: String): Flowable<Either<Errors, String>> =
        remoteDataSource.login(telephone, password)
            .doOnNext { either ->
                either.fold({

                }, {
                    localDataSource.savePrefsToken(it)
                })
            }

    fun myInfo(username: String, password: String): Flowable<Either<Errors, UserInfo>> {
        return remoteDataSource.myInfo(username, password)
            .doOnNext { either ->
                either.fold({

                }, {
                    localDataSource.saveUserInfo(it)
                })
            }
            .doOnNext { either ->
                either.fold({

                }, {
                    UserManager.INSTANCE = it
                })
            }
    }

    fun prefsUser(): Flowable<Either<Errors, String>> =
        localDataSource.fetchPrefsUser()

    fun prefsAutoLogin(): Single<Boolean> =
        localDataSource.isAutoLogin()

    fun findDefaultUserInfo(): Single<Either<Errors, UserInfo>> =
        localDataSource.findDefaultUserInfo()
}

class LoginRemoteDataSource(private var remote: ApiService) : ILoginRemoteDataSource {

    override fun login(telephone: String, password: String): Flowable<Either<Errors, String>> =
        remote.login(telephone, password)
            .subscribeOn(RxSchedulers.io)
            .map {
                when (it.code) {
                    200 -> Either.right(it.data)
                    else -> Either.left(Errors.Error(it.code, it.msg!!))
                }
            }

    override fun myInfo(username: String, password: String): Flowable<Either<Errors, UserInfo>> =
        remote.myInfo()
            .subscribeOn(RxSchedulers.io)
            .map { myInfo ->
                when (myInfo.code) {
                    200 -> {
                        Either.right(
                            UserInfo(
                                1, username, password, myInfo.data.avatar,
                                myInfo.data.department.id, myInfo.data.email, myInfo.data.gender, myInfo.data.id,
                                myInfo.data.job.id, myInfo.data.mobile, myInfo.data.name, myInfo.data.salt,
                                myInfo.data.status
                            )
                        )
                    }
                    else -> Either.left(Errors.Error(myInfo.code, myInfo.msg!!))
                }
            }

}

class LoginLocalDataSource(private var local: AppDatabase, private val prefs: PrefsHelper) :
    ILoginLocalDataSource {

    override fun saveUserInfo(userInfo: UserInfo) {
        local.userInfoDao().insert(userInfo)
    }

    override fun savePrefsToken(token: String) {
        prefs.token = token
    }

    override fun fetchPrefsUser(): Flowable<Either<Errors, String>> =
        Flowable.just(prefs)
            .map {
                when (it.token.isNotEmpty()) {
                    false -> Either.left(Errors.EmptyResultsError)
                    true -> Either.right(it.token)
                }
            }


    override fun isAutoLogin(): Single<Boolean> =
        Single.just(prefs.autoLogin)

    override fun findDefaultUserInfo(): Single<Either<Errors, UserInfo>> =
        local.userInfoDao().findDefaultUserInfo(1)
            .subscribeOn(RxSchedulers.io)
            .map {
                Either.right(it)
            }
}

interface ILoginRemoteDataSource : IRemoteDataSource {

    fun login(telephone: String, password: String): Flowable<Either<Errors, String>>

    fun myInfo(username: String, password: String): Flowable<Either<Errors, UserInfo>>
}

interface ILoginLocalDataSource : ILocalDataSource {

    fun savePrefsToken(token: String)

    fun fetchPrefsUser(): Flowable<Either<Errors, String>>

    fun isAutoLogin(): Single<Boolean>

    fun findDefaultUserInfo(): Single<Either<Errors, UserInfo>>

    fun saveUserInfo(userInfo: UserInfo)
}