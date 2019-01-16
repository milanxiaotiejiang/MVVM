package com.huaqing.property.ui.main.repos

import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService

class ReposDataSourceRepository(
    remoteDataSource: IRemoteReposDataSource
) : BaseRepositoryRemote<IRemoteReposDataSource>(remoteDataSource) {

}

class ReposRemoteDataSource(private var remote: ApiService) : IRemoteReposDataSource {

}

interface IRemoteReposDataSource : IRemoteDataSource {
}