package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entity.MyMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(myMovie: MyMovie)

    @Query("SELECT * FROM myMovies")
    fun selectAll(): Flow<List<MyMovie>>

    @Query("DELETE FROM myMovies WHERE id = :id")
    suspend fun deleteMovie(id: Int)
}