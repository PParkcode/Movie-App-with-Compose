package com.example.presentation.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.domain.model.MovieCover
import com.example.presentation.home.onClickMovie
import com.example.presentation.search.ui.theme.ComposeMovieTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun SearchScreenSetUp(viewModel: SearchViewModel = hiltViewModel()) {

    SearchMainScreen(
        keyword = viewModel.keyword,
        result = viewModel.movieList,
        viewModel = viewModel
        //searchMovies = {viewModel.searchMovies(it) }

    )

}
@Composable
fun SearchMainScreen(
    keyword:String,
    result:List<MovieCover>,
    viewModel:SearchViewModel
    //searchMovies:(String)->Unit

) {

    val coroutineScope = rememberCoroutineScope()

    var textState by remember { mutableStateOf("") }

    val onTextChange = { text:String ->
        textState = text


        coroutineScope.launch() {
            delay(200)
            viewModel.searchMovies(text)
        }



    }

    Column{
        InputArea(textState = textState, onTextChange =  { onTextChange(it) } , modifier = Modifier)
        ResultArea(viewModel.movieList)
    }
}

@Composable
fun InputArea( textState: String,
               onTextChange: (String) -> Unit,
               modifier: Modifier = Modifier) {

    OutlinedTextField(
        value =  textState,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        label = {Text("검색어를 입력해주세요")},
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp),
        modifier = Modifier.padding(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )


}

@Composable
fun ResultArea(movieList:List<MovieCover>) {
    if(movieList.isEmpty()) {

    }
    else {
        LazyVerticalGrid(
            columns  = GridCells.Adaptive(75.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(movieList) {movie->
                MovieItem(movie.title,movie.posterUrl)

            }
        }
    }

}


@Composable
fun MovieItem(title:String = "Sample",
              url:String = "",
              modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .then(modifier)
            .size(width = 75.dp,height=140.dp)
            .padding(top = 5.dp)
            .clickable { onClickMovie(title, url) },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp),


        ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500"+url,
            contentDescription = null,
            Modifier.fillMaxSize()
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    ComposeMovieTheme {
    }
}