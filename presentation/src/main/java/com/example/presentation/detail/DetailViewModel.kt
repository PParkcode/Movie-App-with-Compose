package com.example.presentation.detail

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieDetail
import com.example.domain.usecase.DbDeleteMovieUseCase
import com.example.domain.usecase.DbGetMyMoviesUseCase
import com.example.domain.usecase.DbInsertMovieUseCase
import com.example.domain.usecase.GetDetailMovieDataUseCase
import com.example.domain.usecase.GetRecommendationsUseCase
import com.example.domain.usecase.GetSimilarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

    //var myMovies: MutableList<MovieCover> by mutableStateOf(mutableListOf())
    var myMovies = MutableStateFlow<List<MovieCover>>(emptyList())
    //var myMovies: MutableStateFlow<List<MovieCover>> = MutableStateFlow(emptyList())


    suspend fun getDetailMovieData(id: Int) {
        /*try {
            detailMovieData = getDetailMovieDataUseCase.invoke(id)
        } catch (e:Exception) {
            Log.d("park",e.toString())
        }

         */
        detailMovieData = getDetailMovieDataUseCase.invoke(id)
    }

    suspend fun getRecommendations(id: Int) {
        recommendations = getRecommendationsUseCase.invoke(id).toMutableList()
    }

    suspend fun getSimilar(id: Int) {
        similar = getSimilarUseCase.invoke(id).toMutableList()
    }

    /*
    TODO 코드가 매우 매우 지저분함. 리팩토링 할 것
     */

    suspend fun insertMovie() {
        /*
        Log.d("park", "InsertMovie on ViewModel")
        detailMovieData!!.poster_path?.let {
            dbInsertMovieUseCase.invoke(
                detailMovieData!!.id, detailMovieData!!.title,
                it
            )
        }
        detailMovieData!!.poster_path?.let {
            MovieCover(
                detailMovieData!!.title,
                detailMovieData!!.id, it, detailMovieData!!.vote_average
            )
        }?.let { myMovies.emit(it) }

         */
        detailMovieData!!.poster_path?.let {
            dbInsertMovieUseCase.invoke(detailMovieData!!.id,detailMovieData!!.title,
                it
            )
        }


    }



    suspend fun deleteMovie() {
        detailMovieData?.let { dbDeleteMovieUseCase.invoke(it.id) }


    }

    fun getMyMovies() {
        viewModelScope.launch {
            val movies = dbGetMyMoviesUseCase.invoke()
            movies.collect { movieList ->
                myMovies.value = movieList
            }
        }

    }

    /*
    fun isLikedorNot() {
        for (item in myMovies) {
            if (item.id == detailMovieData!!.id) {
                isLiked == true
                return
            }
        }
        isLiked == false
        return
    }

     */

}