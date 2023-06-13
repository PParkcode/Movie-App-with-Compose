package com.example.data.dataSource

import com.example.data.model.MovieData
import com.example.data.network.Api
import javax.inject.Inject

//internal class -> class
class MovieDataSourceImpl @Inject constructor(
    private var client: Api
): MovieDataSource {

    override suspend fun getPopularMovie(): List<MovieData> {

        return client.getPopularMovie().results
    }

    override suspend fun getNowPlaying(): List<MovieData> {
        return client.getNowPlaying().results
    }

    override suspend fun getUpComing(): List<MovieData> {
        return client.getUpComing().results
    }

    override suspend fun getTopRated(): List<MovieData> {
        return client.getTopRated().results
    }
}