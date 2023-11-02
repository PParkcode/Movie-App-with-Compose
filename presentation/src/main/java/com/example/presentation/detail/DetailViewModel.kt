package com.example.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieDetail
import com.example.domain.usecase.DbDeleteMovieUseCase
import com.example.domain.usecase.DbGetMyMoviesUseCase
import com.example.domain.usecase.DbInsertMovieUseCase
import com.example.domain.usecase.GetDetailMovieDataUseCase
import com.example.domain.usecase.GetRecommendationsUseCase
import com.example.domain.usecase.GetSimilarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieDataUseCase: GetDetailMovieDataUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getSimilarUseCase: GetSimilarUseCase,
    private val dbInsertMovieUseCase: DbInsertMovieUseCase,
    private val dbDeleteMovieUseCase: DbDeleteMovieUseCase,
    private val dbGetMyMoviesUseCase: DbGetMyMoviesUseCase
) : ViewModel() {

    var detailMovieData by mutableStateOf<MovieDetail?>(null)
    var isLiked by mutableStateOf(false)
    var recommendations: MutableList<MovieCover> by mutableStateOf(mutableListOf())
    var similar: MutableList<MovieCover> by mutableStateOf(mutableListOf())

    val myMovies: Flow<List<MovieCover>> = dbGetMyMoviesUseCase.invoke().flowOn(
        Dispatchers.IO
    )


    suspend fun getDetailMovieData(id: Int) {
        detailMovieData = getDetailMovieDataUseCase.invoke(id)
    }

    suspend fun getRecommendations(id: Int) {
        recommendations = getRecommendationsUseCase.invoke(id).toMutableList()
    }

    suspend fun getSimilar(id: Int) {
        similar = getSimilarUseCase.invoke(id).toMutableList()
    }

    suspend fun insertMovie() {

        detailMovieData!!.poster_path?.let {
            dbInsertMovieUseCase.invoke(
                detailMovieData!!.id, detailMovieData!!.title,
                it
            )
        }
    }
    suspend fun deleteMovie() {
        detailMovieData?.let { dbDeleteMovieUseCase.invoke(it.id) }


    }
}