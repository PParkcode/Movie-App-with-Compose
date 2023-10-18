package com.example.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "myMovies")
data class MyMovie(
    @PrimaryKey
    val id: Int,
    val movie_title:String,
    val poster_path:String
)
