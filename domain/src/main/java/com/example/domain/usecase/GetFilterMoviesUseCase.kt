package com.example.domain.usecase

import com.example.domain.model.MovieCover
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetFilterMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(withGenres:String, Stars:String, withoutGenres:String): List<MovieCover> {
        return repository.getFilterMovies(withGenres, Stars, withoutGenres)
    }
}