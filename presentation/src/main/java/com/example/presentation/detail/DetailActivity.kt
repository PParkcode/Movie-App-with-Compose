package com.example.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieDetail
import com.example.presentation.R
import com.example.presentation.detail.ui.theme.ComposeMovieTheme
import com.example.presentation.home.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DetailActivity : ComponentActivity() {
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
fun SetDetailActivity(
    id: Int,
    navigateToDetail: (Int) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val myMovies by viewModel.myMovies.collectAsState(initial = emptyList())
    viewModel.isLiked = myMovies.any { it.id == id }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            viewModel.getDetailMovieData(id)
            viewModel.getRecommendations(id)
            viewModel.getSimilar(id)


        }
    }
    viewModel.detailMovieData?.let {
        DetailScreen(
            it,
            viewModel.isLiked,
            recommendations = viewModel.recommendations,
            similar = viewModel.similar,
            { navigateToDetail(it) },
            onClickToDelete = { viewModel.deleteMovie() },
            onClickToInsert = { viewModel.insertMovie() },
            coroutineScope = coroutineScope

        )
    }
}

@Composable
fun DetailScreen(
    movie: MovieDetail,
    isLiked: Boolean,
    recommendations: List<MovieCover>,
    similar: List<MovieCover>,
    navigateToDetail: (Int) -> Unit,
    onClickToInsert: suspend () -> Unit,
    onClickToDelete: suspend () -> Unit,
    coroutineScope: CoroutineScope
) {
    Column {
        movie.backdrop_path?.let { ImageArea(imageUrl = it) }

        BottomScrollArea(
            movie = movie,
            isLiked = isLiked,
            recommendations = recommendations,
            similar = similar,
            navigateToDetail = { navigateToDetail(it) },
            onClickToInsert = { onClickToInsert() },
            onClickToDelete = { onClickToDelete() },
            coroutineScope = coroutineScope,
            modifier = Modifier.padding(
                top = 7.dp, start = 15.dp, end = 15.dp
            )
        )
    }
}

@Composable
fun ImageArea(imageUrl: String) {
    if (imageUrl == "") {

    } else {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = "https://image.tmdb.org/t/p/original" + imageUrl,
            contentDescription = null
        )
    }

}

@Composable
fun BottomScrollArea(
    movie: MovieDetail,
    isLiked: Boolean,
    recommendations: List<MovieCover>,
    similar: List<MovieCover>,
    navigateToDetail: (Int) -> Unit,
    onClickToInsert: suspend () -> Unit,
    onClickToDelete: suspend () -> Unit,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {

    LazyColumn {
        item {
            TitleDescriptionArea(movie = movie, modifier = modifier)
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                HeartButton(
                    isLiked = isLiked,
                    onClickToInsert = { onClickToInsert() },
                    onClickToDelete = { onClickToDelete() },
                    coroutineScope = coroutineScope
                )
                CommentButton()

            }
        }
        item {
            TabArea(recommendations, similar, { navigateToDetail(it) })
        }
    }

}

@Composable
fun TitleDescriptionArea(movie: MovieDetail, modifier: Modifier) {

    var isClicked by remember { mutableStateOf(false) }

    val onClickText = {
        Log.d("park", "onClickText")
        isClicked = !isClicked
    }
    Column(modifier = modifier) {

        Text(
            text = movie.title,
            modifier = Modifier,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${movie.release_date}개봉", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "${movie.runtime}분",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "평점: ${movie.vote_average}",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        TextBox(
            text = if (movie.overview == "") "영화 정보가 없습니다." else movie.overview,
            isClicked,
            onClickText
        )

        LazyRow(
            contentPadding = PaddingValues(vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            items(movie.genres) { genre ->
                GenreBox(genreName = genre.name, modifier = Modifier.padding(end = 10.dp))
            }
        }
    }
}

@Composable
fun TextBox(
    text: String,
    isClicked: Boolean,
    onClickText: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .sizeIn(minWidth = 50.dp, minHeight = 25.dp)
            .clickable { onClickText() }
            .padding(top = 7.dp, bottom = 7.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),

        ) {
        Text(
            text,
            modifier = Modifier.padding(10.dp),
            fontSize = 13.sp,
            color = Color.Black,
            lineHeight = 15.sp,
            maxLines = if (isClicked) 200 else 4,
            overflow = if (isClicked) TextOverflow.Visible else TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GenreBox(genreName: String, modifier: Modifier = Modifier) {
    Box(
        Modifier
            .sizeIn(minWidth = 50.dp, minHeight = 25.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(5.dp)
    ) {
        Text(
            genreName,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 8.sp,
            color = Color.White
        )
    }
}


@Composable
fun HeartButton(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
    onClickToInsert: suspend () -> Unit,
    onClickToDelete: suspend () -> Unit,
    coroutineScope: CoroutineScope
) {


    val heartIcon = if (isLiked) {
        painterResource(id = R.drawable.baseline_favorite_24) // 이미 좋아요한 상태에 대한 아이콘
    } else {
        painterResource(id = R.drawable.baseline_favorite_border_24) // 좋아요하지 않은 상태에 대한 아이콘
    }

    IconButton(
        onClick = {
            coroutineScope.launch {
                if (isLiked) {
                    onClickToDelete()

                } else {
                    onClickToInsert()
                }
            }
        },
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
    ) {
        Icon(
            painter = heartIcon,
            contentDescription = "찜", // 아이콘의 내용을 나타내는 문자열
            tint = if (isLiked) Color("#D32F2F".toColorInt()) else Color.Black
        )

    }
}

@Composable
fun CommentButton() {
    val commentIcon = painterResource(id = R.drawable.baseline_comment_24)
    IconButton(
        onClick = {/*TODO*/ },
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp),

        ) {
        Icon(
            painter = commentIcon,
            contentDescription = "평가", // 아이콘의 내용을 나타내는 문자
        )
    }

}

@Composable
fun TabArea(
    recommendations: List<MovieCover>,
    similar: List<MovieCover>,
    navigateToDetail: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("추천 컨텐츠", "비슷한 컨텐츠")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTabIndex) {
            0 -> TabContent(recommendations, { navigateToDetail(it) })
            1 -> TabContent(similar, { navigateToDetail(it) })
        }
    }
}

@Composable
fun TabContent(recommendations: List<MovieCover>, navigateToDetail: (Int) -> Unit) {


    val items = recommendations.take(8)

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        items(items) { item ->
            MovieItem(
                navigateToDetail = { navigateToDetail(it) },
                id = item.id,
                url = item.posterUrl
            )

        }
    }


}

@Composable
fun ExpandableText(text: String, maxLines: Int) {
    var expanded by remember { mutableStateOf(false) }

    val annotatedText = buildAnnotatedString {
        append(text)
        if (expanded) {
            pushStringAnnotation("Expand", "Collapse")
            withStyle(
                style = SpanStyle(color = androidx.compose.ui.graphics.Color.Gray)
            ) {
                append(" 접기")
            }
            pop()
        } else if (text.count { it == '\n' } > maxLines) {
            pushStringAnnotation("Collapse", "Expand")
            withStyle(
                style = SpanStyle(color = Color.Gray)
            ) {
                append(" ... 더보기")
            }
            pop()
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { annotation ->
                    when (annotation.tag) {
                        "Expand" -> expanded = false
                        "Collapse" -> expanded = true
                    }
                }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ComposeMovieTheme {

    }
}