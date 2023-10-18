package com.example.domain.usecase

import com.example.domain.model.MovieCover
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke():List<MovieCover> {
        return repository.getPopularMovie()
    }
}