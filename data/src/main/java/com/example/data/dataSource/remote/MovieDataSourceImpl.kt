package com.example.data.dataSource.remote

import android.util.Log
import com.example.data.dataSource.remote.MovieDataSource
import com.example.data.model.MovieData
import com.example.data.model.MovieDetailResponse
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

    override suspend fun getSearchResult(keyword:String): List<MovieData> {
        return client.getSearchResult(keyword).results
    }

    override suspend fun getFilterMovies(withGenres:String, Stars:String, withoutGenres:String): List<MovieData> {
        return client.getFilterMovies(withGenres, Stars, withoutGenres).results
    }

    override suspend fun getDetailMovieData(id: Int): MovieDetailResponse {
        Log.d("park","id: $id")
        return client.getDetailMovieData(id)
    }

    override suspend fun getRecommendations(id: Int): List<MovieData> {
        return client.getRecommendations(id).results
    }

    override suspend fun getSimilar(id: Int): List<MovieData> {
        return client.getSimilar(id).results
    }
}