package com.huaqing.property.ui.main.profile

import com.huaqing.property.base.repository.BaseRepositoryRemote
import com.huaqing.property.base.repository.IRemoteDataSource
import com.huaqing.property.common.ApiService

class ProfileDataSourceRepository(
    remoteDataSource: IRemoteProfileDataSource
) : BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource) {

}

class ProfileRemoteDataSource(private var remote: ApiService) : IRemoteProfileDataSource {

}

interface IRemoteProfileDataSource : IRemoteDataSource {
}