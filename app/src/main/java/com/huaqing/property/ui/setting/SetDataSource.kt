package com.huaqing.property.ui.setting

import com.huaqing.property.base.repository.BaseRepositoryLocal
import com.huaqing.property.base.repository.ILocalDataSource
import com.huaqing.property.common.helper.PrefsHelper
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.manager.UserManager
import com.huaqing.property.db.AppDatabase
import com.huaqing.property.db.UserInfo
import com.huaqing.property.utils.logger.log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class SetDataSourceRepository(
    local: ISetLocalDataSource
) : BaseRepositoryLocal<ISetLocalDataSource>(local) {

    fun logout(): Flowable<Unit> = Flowable.just(Unit)
        .doOnNext {
            localDataSource.deleteUserInfo(UserManager.INSTANCE)
        }
        .doOnNext {
            localDataSource.clearPrefsToken()
        }
        .subscribeOn(RxSchedulers.io)
}

class SetLocalDataSource(private var local: AppDatabase, private val prefs: PrefsHelper) :
    ISetLocalDataSource {

    override fun clearPrefsToken() {
        prefs.token = ""
    }

    override fun deleteUserInfo(userInfo: UserInfo) {
        local.userInfoDao().delete(userInfo)
    }
}

interface ISetLocalDataSource : ILocalDataSource {

    fun clearPrefsToken()

    fun deleteUserInfo(userInfo: UserInfo)
}