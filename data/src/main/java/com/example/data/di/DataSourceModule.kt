package com.example.data.di

import com.example.data.dataSource.MovieDataSource
import com.example.data.dataSource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindDataSource(
        dataSource:MovieDataSourceImpl
    ):MovieDataSource
}