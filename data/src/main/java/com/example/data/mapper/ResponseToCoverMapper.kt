package com.example.data.mapper

import com.example.domain.model.MovieCover


inline fun toCover(title: String, url:String, star: String) = MovieCover(title =title, posterUrl = url,star=star)