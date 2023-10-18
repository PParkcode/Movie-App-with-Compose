package com.example.domain.usecase

import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class DbInsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id:Int, title:String, poster_path:String) {
        repository.insertMovie(id,title,poster_path)
    }
}