package com.example.data.di

import com.example.data.dataSource.local.MovieLocalDataSource
import com.example.data.dataSource.local.MovieLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSource: MovieLocalDataSourceImpl
    ): MovieLocalDataSource
}