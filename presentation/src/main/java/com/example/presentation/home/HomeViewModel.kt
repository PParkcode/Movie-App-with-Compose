package com.example.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieCover
import com.example.domain.usecase.GetPopularMovieUseCase
import com.example.domain.usecase.GetNowPlayingUseCase
import com.example.domain.usecase.GetTopRatedUseCase
import com.example.domain.usecase.GetUpComingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getNowPlayingUseCase: GetNowPlayingUseCase,
    private val getUpComingUseCase: GetUpComingUseCase,
    private val getTopRatedUseCase: GetTopRatedUseCase
):ViewModel() {
    var popularMovieList : MutableList<MovieCover> by mutableStateOf(mutableListOf())
    var nowPayingMovies: MutableList<MovieCover> by mutableStateOf(mutableListOf())
    var upComingMovies:MutableList<MovieCover> by mutableStateOf(mutableListOf())
    var topRatedMovies:MutableList<MovieCover> by mutableStateOf(mutableListOf())
    suspend fun getPopularMovie() {
        popularMovieList =getPopularMovieUseCase.invoke().toMutableList()
        Log.d("Main",popularMovieList.toString())
    }

    suspend fun getNowPlaying() {
        nowPayingMovies = getNowPlayingUseCase.invoke().toMutableList()
    }

    suspend fun getUpComing() {
        upComingMovies = getUpComingUseCase.invoke().toMutableList()
    }

    suspend fun getTopRated() {
        topRatedMovies = getTopRatedUseCase.invoke().toMutableList()
    }
}