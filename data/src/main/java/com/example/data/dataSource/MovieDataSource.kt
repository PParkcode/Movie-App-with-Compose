package com.example.data.dataSource

import com.example.data.model.MovieData
import com.example.data.model.MovieDataList

interface MovieDataSource {

    suspend fun getPopularMovie(): List<MovieData>

    suspend fun getNowPlaying(): List<MovieData>

    suspend fun getUpComing(): List<MovieData>

    suspend fun getTopRated(): List<MovieData>
}