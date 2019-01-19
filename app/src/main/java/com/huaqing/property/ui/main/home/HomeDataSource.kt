package com.huaqing.property.ui.main.home

import arrow.core.Either
import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.model.MessageData
import io.reactivex.Flowable

class HomeDataSourceRepository(
    remoteDataSource: IRemoteHomeDataSource
) : BaseRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

    fun messageList(): Flowable<Either<Errors, List<MessageData>>> =
        remoteDataSource.messageList()
}

class HomeRemoteDataSource(private var remote: ApiService) : IRemoteHomeDataSource {
    override fun messageList(): Flowable<Either<Errors, List<MessageData>>> =
        remote.messageList()
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

interface IRemoteHomeDataSource : IRemoteDataSource {
    fun messageList(): Flowable<Either<Errors, List<MessageData>>>
}