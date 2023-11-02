package com.example.presentation.myPage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.MovieCover
import com.example.presentation.search.ResultArea

class MyPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.presentation.myPage.ui.theme.ComposeMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun SetMyPageActivity(
    navigateToDetail: (Int) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val myMovies by viewModel.movieListFlow.collectAsState(initial = emptyList())

    MyPageScreen({ navigateToDetail(it) },myMovies = myMovies)

}


@Composable
fun MyPageScreen(
    navigateToDetail: (Int) -> Unit,
    myMovies: List<MovieCover>,
    modifier: Modifier = Modifier
) {
    Column {
        Box(Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                "내가 찜한 영화",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
        }

        MyMovieList({ navigateToDetail(it) }, myMovies)
    }

}

@Composable
fun MyMovieList(navigateToDetail: (Int) -> Unit, movieList: List<MovieCover>) {
    Column {
        ResultArea(navigateToDetail = { navigateToDetail(it) }, movieList = movieList)
    }

}

