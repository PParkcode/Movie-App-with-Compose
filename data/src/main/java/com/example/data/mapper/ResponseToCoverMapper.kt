package com.example.data.mapper

import com.example.domain.model.MovieCover


inline fun toCover(title: String, url: String, id: Int, star : String) =
    MovieCover(title = title, posterUrl = url, id = id, star = star)