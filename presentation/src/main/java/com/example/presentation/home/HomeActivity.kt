package com.example.presentation.home

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.MovieCover
import com.example.presentation.home.ui.theme.ComposeMovieTheme
import com.example.presentation.home.ui.theme.Purple80
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.presentation.detail.SetDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //HomeScreen(Modifier.fillMaxSize())
                }
            }
        }
    }
}

/**
 * TODO 인터넷 연결에 대한 에러 처리가 필요하다
 *
 */

@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.getPopularMovie()
            viewModel.getNowPlaying()
            viewModel.getUpComing()
            viewModel.getTopRated()
        }
    }


    LazyColumn {

        item {
            UpScreen(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }
        item {
            DownScreen(
                navigateToDetail = { navigateToDetail(it) },
                popularList = viewModel.popularMovieList,
                nowPlayingList = viewModel.nowPayingMovies,
                upComingList = viewModel.upComingMovies,
                topRatedList = viewModel.topRatedMovies
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .background(color = Purple80)
    ) {
        Text(
            "이런 영화들은\n어떤가요",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun DownScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    popularList: List<MovieCover>,
    nowPlayingList: List<MovieCover>,
    upComingList: List<MovieCover>,
    topRatedList: List<MovieCover>
) {
    MovieList({ navigateToDetail(it) }, "요즘 핫한 영화", modifier, popularList)
    MovieList({ navigateToDetail(it) }, "높은 평점을 받은 작품들", modifier, topRatedList)
    MovieList({ navigateToDetail(it) }, "현재 상영하는 작품들", modifier, nowPlayingList)
    MovieList({ navigateToDetail(it) }, "개봉 예정 작품들", modifier, upComingList)
    Spacer(modifier = Modifier.size(100.dp))
}

@Composable
fun MovieList(
    navigateToDetail: (Int) -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    itemList: List<MovieCover>
) {

    Column(
        modifier
            .then(modifier)
            .background(Color.White)
            .then(modifier)
            .padding(start = 10.dp, top = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

            items(itemList) { movie ->
                MovieItem(
                    navigateToDetail = { navigateToDetail(it) },
                    movie.id,
                    movie.posterUrl,
                    modifier.size(width = 120.dp, height = 180.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    }
}


@Composable
fun MovieItem(
    navigateToDetail: (Int) -> Unit,
    id: Int,
    url: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .padding(top = 5.dp)
            .clickable {
                navigateToDetail(id)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),


        ) {
        if(url=="") {
            Box( contentAlignment = Alignment.Center, modifier = Modifier.size(width = 75.dp, height = 120.dp)){

                Text("No Image", fontSize = 7.sp)
            }
        } else {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original$url",
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

    }

}


