package com.example.domain.model

data class MovieDetail (
    val backdrop_path: String?,
    //val belongs_to_collection: List<MovieCollection>?, // You can replace Any? with the actual data class if available
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: String,
    val vote_count: Int
        )

data class Genre(
    val id: Int,
    val name: String
)

