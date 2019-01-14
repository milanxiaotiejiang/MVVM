package com.huaqing.property.ui.login

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import arrow.core.Either
import arrow.core.right
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
import com.huaqing.property.model.MyInfoBean
import com.huaqing.property.utils.logger.log
import io.reactivex.Flowable
import io.reactivex.Single

class LoginDataSourceRepository(
    remote: LoginLocalDataSource.ILoginRemoteDataSource,
    local: LoginLocalDataSource.ILoginLocalDataSource
) : BaseRepositoryBoth<LoginDataSourceRepository.ILoginRemoteDataSource, LoginDataSourceRepository.ILoginLocalDataSource>(
    remote,
    local
) {

    fun login(telephone: String, password: String): Flowable<Either<Errors, String>> =
        remoteDataSource.login(telephone, password)
            .doOnNext { either ->
                either.fold({

                }, {
                    localDataSource.savePrefsUser(it)
                })
            }


    fun prefsUser(): Flowable<Either<Errors, String>> =
        localDataSource.fetchPrefsUser()

    fun prefsAutoLogin(): Single<Boolean> =
        localDataSource.isAutoLogin()

    fun myInfo(username: String) {
//        return remoteDataSource.myInfo()
//            .doOnNext { either ->
//                either.fold({
//
//                }, {
//                    localDataSource.saveUserInfo(it, username)
//                })
//            }
//            .doOnNext { either ->
//                either.fold({
//
//                }, {
//                    UserManager.INSTANCE = it
//                })
//            }
        localDataSource.saveUserInfo()
    }

}

class LoginRemoteDataSource(private var remote: ApiService) : ILoginRemoteDataSource {
    override fun myInfo(): Flowable<Either<Errors, MyInfoBean>> =
        remote.myInfo()
            .subscribeOn(RxSchedulers.io)
            .map {
                when (it.code) {
                    200 -> Either.right(it)
                    else -> Either.left(Errors.Error(it.code, it.msg!!))
                }
            }

    override fun login(telephone: String, password: String): Flowable<Either<Errors, String>> =
        remote.login(telephone, password)
            .subscribeOn(RxSchedulers.io)
            .map {
                when (it.code) {
                    200 -> Either.right(it.data)
                    else -> Either.left(Errors.Error(it.code, it.msg!!))
                }
            }
}

class LoginLocalDataSource(private var local: AppDatabase, private val prefs: PrefsHelper) :
    ILoginLocalDataSource {
    override fun saveUserInfo(myInfo: MyInfoBean, username: String) {

        local.userInfoDao().findUserByUserName(username)
            .doOnNext {
                log {
                    "" + it.size
                }
            }
//
//        val  = local.userInfoDao().findUserByUserName(username)
//
//        local.userInfoDao().findUserByUserName(username)
//            .doOnNext {
//                when (it == null) {
//                    false -> {
//                        var userInfo = UserInfo(
//                            1, myInfo.data.username, myInfo.data.password, myInfo.data.avatar,
//                            myInfo.data.department.id, myInfo.data.email, myInfo.data.gender, myInfo.data.id,
//                            myInfo.data.job.id, myInfo.data.mobile, myInfo.data.name, myInfo.data.salt,
//                            myInfo.data.status
//                        )
//                        Flowable.just(userInfo)
//                    }
//                    true -> {
//                        local.userInfoDao().delete(it!!.id)
//                        Flowable.just(it)
//                    }
//                }
//            }
//            .doOnNext {
//                when (it != null) {
//                    false ->
//                        Flowable.just(Either.left(Errors.EmptyResultsError))
//                    true -> {
//                        local.userInfoDao().insert(it)
//                        Flowable.just(Either.right(it))
//                    }
//                }
//            }
    }

    override fun savePrefsUser(token: String) {
        prefs.token = token
    }

    override fun findDefaultUserInfo() =
        local.userInfoDao().findDefaultUserInfo(1)
            .map {
                when (it == null) {
                    false ->
                        Either.left(Errors.EmptyResultsError)
                    true ->
                        Either.right(it)
                }
            }

    override fun isAutoLogin(): Single<Boolean> =
        Single.just(prefs.autoLogin)
}

interface ILoginRemoteDataSource : IRemoteDataSource {

    fun login(telephone: String, password: String): Flowable<Either<Errors, String>>

    fun myInfo(): Flowable<Either<Errors, MyInfoBean>>
}

interface ILoginLocalDataSource : ILocalDataSource {

    fun savePrefsUser(token: String)

    fun findDefaultUserInfo()

    fun isAutoLogin(): Single<Boolean>

    fun saveUserInfo(myInfo: MyInfoBean, username: String)
}