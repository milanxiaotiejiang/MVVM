package com.huaqing.property.ui.workorder

import arrow.core.Either
import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.common.manager.UserManager
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.model.WorkOrderData
import io.reactivex.Flowable

class WorkDataSourceRepository(
    remoteDataSource: IRemoteWorkDataSource
) : BaseRepositoryRemote<IRemoteWorkDataSource>(remoteDataSource) {

    fun workOrderList(status: String): Flowable<Either<Errors, List<WorkOrderData>>> =
        remoteDataSource.workOrderList(status)
}

class WorkRemoteDataSource(private var remote: ApiService) : IRemoteWorkDataSource {
    override fun workOrderList(status: String): Flowable<Either<Errors, List<WorkOrderData>>> =
        remote.workOrderList(UserManager.INSTANCE.infoId, status)
            .compose(globalHandleError())
            .subscribeOn(RxSchedulers.io)
            .map {
                when (it.code) {
                    200 -> {
                        val list = it.data
                        when (list.isEmpty()) {
                            true -> Either.left(Errors.EmptyResultsError)
                            false -> Either.right(list)
                        }
                    }
                    else -> Either.left(Errors.Error(it.code, it.msg!!))
                }
            }

}

interface IRemoteWorkDataSource : IRemoteDataSource {
    fun workOrderList(status: String): Flowable<Either<Errors, List<WorkOrderData>>>
}