package com.example.data.dataSource

import com.example.data.model.MovieData
import com.example.data.model.MovieDataList

interface MovieDataSource {

    suspend fun getPopularMovie(): List<MovieData>

    suspend fun getNowPlaying(): List<MovieData>

    suspend fun getUpComing(): List<MovieData>

    suspend fun getTopRated(): List<MovieData>

    suspend fun getSearchResult(keyword:String):List<MovieData>

    suspend fun getFilterMovies(withGenres:String, Stars:String, withoutGenres:String):List<MovieData>
}