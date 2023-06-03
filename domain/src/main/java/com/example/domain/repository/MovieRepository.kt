package com.example.domain.repository

import com.example.domain.model.MovieCover
import com.example.domain.model.MovieCoverList

interface MovieRepository {

    suspend fun getPopularMovie() :List<MovieCover>
}