package com.example.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieCoverList
import com.example.domain.usecase.GetPopularMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
):ViewModel() {
    var popularMovieList : MutableList<MovieCover> by mutableStateOf(mutableListOf())

    suspend fun getPopularMovie() {
        popularMovieList =getPopularMovieUseCase.invoke().toMutableList()
        Log.d("Main",popularMovieList.toString())
    }
}