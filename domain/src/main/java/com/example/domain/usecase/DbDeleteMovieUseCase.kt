package com.example.domain.usecase

import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class DbDeleteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(id:Int) {
        repository.deleteMovie(id)
    }
}