package com.example.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieCover
import com.example.domain.usecase.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase
) : ViewModel() {

    val keyword by mutableStateOf("")
    var movieList: MutableList<MovieCover> by mutableStateOf(mutableListOf())

    suspend fun searchMovies(keyword:String) {
        Log.d("park","SearchMovies시작")
        try {
            movieList = getSearchResultUseCase.invoke(keyword).toMutableList()

        } catch (e:Exception) {
            Log.d("park","에러 발생 ${e.toString()}")
        }

    }
}