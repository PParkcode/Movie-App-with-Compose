package com.example.domain.repository

import com.example.domain.model.MovieCover
import com.example.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getPopularMovie() :List<MovieCover>

    suspend fun getNowPlaying(): List<MovieCover>

    suspend fun getUpComing(): List<MovieCover>

    suspend fun getTopRated(): List<MovieCover>

    suspend fun getSearchResult(keyword:String): List<MovieCover>

    suspend fun getFilterMovies(withGenres:String, Stars:String, withoutGenres:String): List<MovieCover>

    suspend fun getDetailMovieData(id:Int): MovieDetail

    suspend fun getRecommendations(id:Int): List<MovieCover>

    suspend fun getSimilar(id:Int): List<MovieCover>


    //DB
    suspend fun insertMovie(id:Int, title:String, poster_path:String)

    suspend fun deleteMovie(id:Int)

    fun getMyMovies(): Flow<List<MovieCover>>
}