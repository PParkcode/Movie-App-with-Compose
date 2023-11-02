package com.example.presentation.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.MovieCover
import com.example.presentation.home.MovieItem

import com.example.presentation.search.ui.theme.ComposeMovieTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
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
fun SearchScreenSetUp(
    navigateToDetail: (Int) -> Unit, viewModel: SearchViewModel = hiltViewModel()
) {

    SearchMainScreen(
        navigateToDetail = { navigateToDetail(it) },
        keyword = viewModel.keyword,
        result = viewModel.movieList,
        viewModel = viewModel
        //searchMovies = {viewModel.searchMovies(it) }

    )

}

@Composable
fun SearchMainScreen(
    navigateToDetail: (Int) -> Unit,
    keyword: String,
    result: List<MovieCover>,
    viewModel: SearchViewModel


) {

    val coroutineScope = rememberCoroutineScope()

    var textState by remember { mutableStateOf("") }

    val onTextChange = { text: String ->
        textState = text


        coroutineScope.launch() {
            delay(200)
            viewModel.searchMovies(text)
        }

    }

    Column {
        InputArea(textState = textState, onTextChange = { onTextChange(it) }, modifier = Modifier)
        ResultArea(navigateToDetail = { navigateToDetail(it) }, viewModel.movieList)
    }
}

@Composable
fun InputArea(
    textState: String, onTextChange: (String) -> Unit, modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        label = { Text("검색어를 입력해주세요") },
        textStyle = TextStyle(
            fontWeight = FontWeight.Normal, fontSize = 18.sp
        ),
        modifier = Modifier.
            fillMaxWidth()
            .padding(10.dp),
    )
}

@Composable
fun ResultArea(navigateToDetail: (Int) -> Unit, movieList: List<MovieCover>) {
    if (movieList.isEmpty()) {
        //TODO
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(75.dp), horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            items(movieList) { movie ->
                MovieItem({ navigateToDetail(it) }, movie.id, movie.posterUrl)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    ComposeMovieTheme {}
}