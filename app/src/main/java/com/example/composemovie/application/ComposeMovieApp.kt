package com.example.composemovie.application

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.presentation.main.MainScreen
import com.example.presentation.main.MainScreenActivity
import com.example.presentation.main.ui.theme.ComposeMovieTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeMovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}