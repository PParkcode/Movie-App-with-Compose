package com.example.domain.usecase

import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetDetailMovieDataUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id:Int):MovieDetail {
        return repository.getDetailMovieData(id)
    }
}