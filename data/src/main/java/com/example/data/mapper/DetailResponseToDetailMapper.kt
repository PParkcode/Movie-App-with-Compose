package com.example.data.mapper

import com.example.data.model.MovieDetailResponse

import com.example.domain.model.MovieDetail


inline fun toDetail(
    movieDetail: MovieDetailResponse
) = MovieDetail(
    movieDetail.backdrop_path?:"",
    //movieDetail.belongs_to_collection,
    movieDetail.genres,
    movieDetail.id,
    movieDetail.overview,
    movieDetail.popularity,
    movieDetail.poster_path,
    movieDetail.release_date,
    movieDetail.runtime,
    movieDetail.status,
    movieDetail.tagline,
    movieDetail.title,
    String.format("%.1f",movieDetail.vote_average),
    movieDetail.vote_count
)