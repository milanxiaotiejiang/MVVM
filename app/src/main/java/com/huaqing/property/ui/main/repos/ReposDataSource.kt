package com.huaqing.property.ui.main.repos

import arrow.core.Either
import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.http.globalHandleError
import com.huaqing.property.model.Errors
import com.huaqing.property.model.InfoData
import io.reactivex.Flowable

class ReposDataSourceRepository(
    remoteDataSource: IRemoteReposDataSource
) : BaseRepositoryRemote<IRemoteReposDataSource>(remoteDataSource) {

    fun addressList(): Flowable<Either<Errors, List<InfoData>>> =
        remoteDataSource.addressList()
}

class ReposRemoteDataSource(private var remote: ApiService) : IRemoteReposDataSource {
    override fun addressList(): Flowable<Either<Errors, List<InfoData>>> =
        remote.addressList()
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

interface IRemoteReposDataSource : IRemoteDataSource {
    fun addressList(): Flowable<Either<Errors, List<InfoData>>>
}