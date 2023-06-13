package com.example.data.network

import com.example.data.model.MovieDataList
import com.example.data.model.MovieResponse
import retrofit2.http.GET

const val MY_KEY = "78f09933ae6d289319bd8eeccafacf23"
const val LANG = "ko-KR"
interface Api {
    // Find movies using over 30 filters and sort options.
    @GET("discover/movie?include_adult=false&include_video=false&language=ko-KR&page=1&sort_by=popularity.desc&api_key=78f09933ae6d289319bd8eeccafacf23")
    suspend fun getMovieList(): MovieDataList

    //getPopularMovie
    @GET("movie/popular?language=ko-KR&page=1&region=KR&api_key=$MY_KEY")
    suspend fun getPopularMovie(): MovieResponse


    //get week trend
    @GET("movie/now_playing?language=$LANG&page=1&region=KR&api_key=$MY_KEY")
    suspend fun getNowPlaying() : MovieResponse

    @GET("movie/upcoming?language=$LANG&page=1&region=KR&api_key=$MY_KEY")
    suspend fun getUpComing(): MovieResponse

    @GET("movie/top_rated?language=$LANG&page=1region=KR&api_key=$MY_KEY")
    suspend fun getTopRated(): MovieResponse
}