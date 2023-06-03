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
}