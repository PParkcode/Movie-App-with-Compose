package com.example.presentation.myPage

import androidx.lifecycle.ViewModel
import com.example.domain.model.MovieCover
import com.example.domain.usecase.DbGetMyMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dbGetMyMoviesUseCase: DbGetMyMoviesUseCase
) : ViewModel() {
    val movieListFlow: Flow<List<MovieCover>> = dbGetMyMoviesUseCase.invoke().flowOn(
        Dispatchers.IO
    )



}