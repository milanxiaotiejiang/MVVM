package com.huaqing.property.ui.main.home

import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService

class HomeDataSourceRepository(
    remoteDataSource: IRemoteHomeDataSource
) : BaseRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

}

class HomeRemoteDataSource(private var remote: ApiService) : IRemoteHomeDataSource {

}

interface IRemoteHomeDataSource : IRemoteDataSource {
}