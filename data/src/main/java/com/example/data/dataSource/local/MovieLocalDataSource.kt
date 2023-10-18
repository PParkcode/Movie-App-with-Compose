package com.example.data.dataSource.local

import com.example.data.db.entity.MyMovie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getMyMovies(): Flow<List<MyMovie>>

    suspend fun addMyMovie(myMovie: MyMovie)

    suspend fun deleteMyMovie(id: Int)
}