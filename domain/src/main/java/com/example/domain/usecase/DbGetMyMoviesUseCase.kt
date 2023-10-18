package com.example.domain.usecase

import com.example.domain.model.MovieCover
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbGetMyMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<MovieCover>> {
        return repository.getMyMovies()
    }
}