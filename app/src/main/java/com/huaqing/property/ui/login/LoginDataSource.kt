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

    fun login(telephone: String, password: String): Flowable<Either<Errors, UserInfo>> =
        remoteDataSource.login(telephone, password)
            .doOnNext { either ->
                either.fold({

                }, {
                    UserManager.INSTANCE = it
                })
            }
            .doOnNext { either ->
                either.fold({

                }, {
                    localDataSource.savePrefsUser(it.telephone!!, it.password!!, it.token!!)
                })
            }


    fun prefsUser(): Flowable<Either<Errors, UserInfo>> =
        localDataSource.fetchPrefsUser()

    fun prefsAutoLogin(): Single<Boolean> =
        localDataSource.isAutoLogin()
}

class LoginRemoteDataSource(private var remote: ApiService) : ILoginRemoteDataSource {
    override fun login(telephone: String, password: String): Flowable<Either<Errors, UserInfo>> =
        remote.login(telephone, password)
            .subscribeOn(RxSchedulers.io)
            .map {
                when (it.code) {
                    200 -> Either.right(UserInfo(1, telephone, password, it.data))
                    else -> Either.left(Errors.Error(it.code, it.msg!!))
                }

            }
}

class LoginLocalDataSource(private var local: AppDatabase, private val prefs: PrefsHelper) : ILoginLocalDataSource {
    override fun savePrefsUser(telephone: String, password: String, token: String) {
        prefs.telephone = telephone
        prefs.password = password
        prefs.token = token
    }

    override fun fetchPrefsUser(): Flowable<Either<Errors, UserInfo>> =
        Flowable.just(prefs)
            .map {
                when (it.telephone.isNotEmpty() && it.password.isNotEmpty() && it.token.isNotEmpty()) {
                    false ->
                        Either.left(Errors.EmptyResultsError)
                    true ->
                        Either.right(UserInfo(1, it.telephone, it.password, it.token))
                }
            }


    override fun isAutoLogin(): Single<Boolean> =
        Single.just(prefs.autoLogin)
}

interface ILoginRemoteDataSource : IRemoteDataSource {
    fun login(telephone: String, password: String): Flowable<Either<Errors, UserInfo>>
}

interface ILoginLocalDataSource : ILocalDataSource {

    fun savePrefsUser(telephone: String, password: String, token: String)

    fun fetchPrefsUser(): Flowable<Either<Errors, UserInfo>>

    fun isAutoLogin(): Single<Boolean>
}