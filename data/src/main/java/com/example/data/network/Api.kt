package com.example.data.network

import com.example.data.model.MovieDataList
import retrofit2.http.GET

interface Api {
    @GET("discover/movie?include_adult=false&include_video=false&language=ko-KR&page=1&sort_by=popularity.desc&api_key=78f09933ae6d289319bd8eeccafacf23")
    suspend fun getMovieList(): MovieDataList
}