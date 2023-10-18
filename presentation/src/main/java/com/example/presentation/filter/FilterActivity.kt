package com.example.presentation.filter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.GenreHash
import com.example.presentation.filter.ui.theme.ComposeMovieTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import com.example.domain.model.MovieCover
import com.example.presentation.home.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class FilterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //SetFilter()
                }
            }
        }
    }
}


// 책 169쪽 흐름 자세히, 책 384쪽
@Composable
fun SetFilter(navigateToDetail: (Int) -> Unit, viewModel: FilterViewModel = hiltViewModel()) {
    BackDropScreen(
        navigateToDetail = navigateToDetail,
        selectedItem = viewModel.selectedChip,
        clickChip = { viewModel.clickChip(it) },
        getFilterMovies = { viewModel.getFilterMovies() },
        resultMovies = viewModel.filterMovieResult
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable


fun BackDropScreen(
    navigateToDetail: (Int) -> Unit,
    selectedItem: List<ChipState>,
    clickChip: (ChipState) -> Unit,
    getFilterMovies: suspend () -> Unit,
    resultMovies: List<MovieCover>

) {
    Log.d("park", "BackDropScreen 재구성")

    val rememberedSelectedItem = remember { selectedItem }
    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Expanded, skipHalfExpanded = true)


    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val maxHeight = configuration.screenHeightDp.dp
    val bottomSheetHeight = with(LocalDensity.current) { 4 * maxHeight / 5 }

    val stars = remember {
        mutableStateListOf(
            ChipState("3.0", 2),
            ChipState("5.0", 2),
            ChipState("7.0", 2),
            ChipState("8.0", 2)
        )
    }

    val genres = GenreHash().getAllKeys()

    /**
     * 이런식으로 초기화하면 재구성 시 무한 재구성에 빠질 수 있음
    val favoriteGenreList = remember { mutableStateListOf<ChipState>() }
    val hateGenreList = remember { mutableStateListOf<ChipState>() }

    for (item in genres) {
    favoriteGenreList.add(ChipState(item, 1))
    hateGenreList.add(ChipState(item, 3))
    }
    아래와 같이 초기화 하자
     **/

    val favoriteGenreList = remember {
        genres.map { item -> ChipState(item, 1) }.toMutableStateList()
    }
    val hateGenreList = remember {
        genres.map { item -> ChipState(item, 3) }.toMutableStateList()
    }
    Log.d("park", "favorite: ${favoriteGenreList.toList()}")

    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomSheetHeight)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FrontScreen(
                    selectedItem = rememberedSelectedItem,
                    clickChip = { clickChip(it) },
                    favoriteGenreList = favoriteGenreList,
                    stars = stars,
                    hateGenreList = hateGenreList,
                    bottomSheetState = bottomSheetState,
                    getFilterMovies = { getFilterMovies() }
                )

            }
        }, sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        BackScreen(
            navigateToDetail = { navigateToDetail(it) },
            scope = scope,
            bottomSheetState = bottomSheetState,
            resultMovies,
            selectedItem = selectedItem,
            selectedItemRow = {

            })

        BackHandler(enabled = bottomSheetState.isVisible) {
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackScreen(
    navigateToDetail: (Int) -> Unit,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    resultMovies: List<MovieCover>,
    selectedItem: List<ChipState>,
    selectedItemRow: @Composable () -> Unit
) {
    Column() {
        Box(Modifier.align(CenterHorizontally)) {
            Text(
                "필터 검색",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 7.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyRow(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedItem) { item ->
                    BottomChip(chipState = item, onClickChip = {}, modifier = Modifier.padding(end = 8.dp))
                }

            }
            Box(contentAlignment = Alignment.CenterEnd) {
                IconButton(onClick = {
                    scope.launch {
                        bottomSheetState.show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                }
            }
        }
        FilterResultArea(navigateToDetail = { navigateToDetail(it) }, movieList = resultMovies)
    }

}

@Composable
fun FilterResultArea(navigateToDetail: (Int) -> Unit, movieList: List<MovieCover>) {
    if (movieList.isEmpty()) {
        //TODO
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(75.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(start = 4.dp,end=4.dp, bottom = 40.dp)
        ) {
            items(movieList) { movie ->
                MovieItem({ navigateToDetail(it) }, movie.id, movie.posterUrl)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FrontScreen(
    selectedItem: List<ChipState>,
    clickChip: (ChipState) -> Unit,
    favoriteGenreList: List<ChipState>,
    stars: List<ChipState>,
    hateGenreList: List<ChipState>,
    bottomSheetState: ModalBottomSheetState,
    getFilterMovies: suspend () -> Unit
) {

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp, bottom = 7.dp)
        ) {
            Text(
                text = "",
                Modifier
                    .alignByBaseline()
                    .padding(start = 3.dp), fontSize = 5.sp
            )
            Text(
                text = "필터",
                Modifier.alignByBaseline(),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { Log.d("park", "selectItem: ${selectedItem.toList()}") }) {
                Text(
                    text = "X",
                    Modifier
                        .alignByBaseline()
                        .padding(end = 11.dp)
                        .clickable {  },
                    fontSize = 17.sp,

                    )
            }
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 7.dp)
        )


        LazyColumn(Modifier.weight(0.7f, false)) {
            item {
                OneCategory(
                    text = "이런 장르가 좋아요!",
                    items = favoriteGenreList,
                    selectedItem = selectedItem,
                    onClickChip = { clickChip(it) }
                )
            }
            item {
                OneCategory(
                    text = "평점이 이 정도는 되야지!",
                    items = stars,
                    selectedItem = selectedItem,
                    onClickChip = { clickChip(it) })
            }
            item {
                OneCategory(
                    text = "이 장르는 싫어요!",
                    items = hateGenreList,
                    selectedItem = selectedItem,
                    onClickChip = { clickChip(it) })
            }
        }
        BottomStickyButton(
            selectedItem = selectedItem,
            bottomSheetState = bottomSheetState,
            onClickChip = { clickChip(it) },
            getFilterMovies = { getFilterMovies() },
            modifier = Modifier.weight(0.2f)
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OneCategory(
    text: String,
    items: List<ChipState>,
    selectedItem: List<ChipState>,
    onClickChip: (ChipState) -> Unit
) {
    Log.d("park", "OneCategory 재구성: selectedItem = ${selectedItem}")
    Box(Modifier.fillMaxWidth()) {

        Column {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
            )
            FlowRow {
                items.forEach { item ->
                    Chip(
                        chipState = item,
                        selectedItem = selectedItem,
                        onClickChip = { onClickChip(item) },
                    )
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomStickyButton(
    modifier: Modifier,
    selectedItem: List<ChipState>,
    bottomSheetState: ModalBottomSheetState,
    onClickChip: (ChipState) -> Unit,
    getFilterMovies: suspend () -> Unit
) {
    Log.d("park", "BottomStickyButton 재구성 $selectedItem")

    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(bottom =16.dp)) {


        LazyRow {
            items(selectedItem) { item ->
                BottomChip(chipState = item, { onClickChip(it) },modifier = Modifier.padding(end = 8.dp))
            }

        }
        Row(
        ) {
            TextButton(onClick = { /*TODO*/ }, Modifier.weight(0.3f).padding(bottom = 24.dp)) {
                Text(text = "초기화")
            }
            Button(onClick = {
                coroutineScope.launch {
                    getFilterMovies()
                    bottomSheetState.hide()
                }
            }, Modifier.weight(0.7f)) {
                Text("적용", color = Color.White)
            }
        }
    }

}

@Composable
fun Chip(
    chipState: ChipState,
    modifier: Modifier = Modifier,
    onClickChip: (ChipState) -> Unit,
    selectedItem: List<ChipState>
) {

    var selected by remember { mutableStateOf(chipState.isSelected) }
    //var selected = remember { mutableStateOf(selectedItem.contains(chipState)) }

    Log.d("park", "chip: ${chipState.name} 재구성 selected: $selected")
    Log.d("park", "chip 주소: ${chipState}")
    if(chipState.category ==3) {
        Box(
            modifier = Modifier
                .sizeIn(minWidth = 50.dp, minHeight = 25.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(if(selected.value) 2.dp else 1.dp, if(selected.value) Color.Red else Color.Black, RoundedCornerShape(16.dp))
                .background( Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable {
                    onClickChip(chipState)
                    selected.value = selectedItem.contains(chipState)
                }


        ) {
            Text(chipState.name, fontSize = 9.sp, color = if (selected.value) Color.Red else Color.Black)
        }
    }

    else {
        Box(
            modifier = Modifier
                .sizeIn(minWidth = 50.dp, minHeight = 25.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(if(selected.value) 2.dp else 1.dp, if(selected.value) Color.Blue else Color.Black, RoundedCornerShape(16.dp))
                .background( Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable {
                    onClickChip(chipState)
                    selected.value = selectedItem.contains(chipState)
                }


        ) {
            Text(chipState.name, fontSize = 9.sp, color = if (selected.value) Color.Blue else Color.Black)
        }
    }

}

@Composable
fun BottomChip(chipState: ChipState, onClickChip: (ChipState) -> Unit, modifier: Modifier) {
    Surface(
        color = when {
            chipState.category == 3 -> Color.Red
            else -> Color.Blue
        },
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Yellow
        ),
        modifier = modifier
    ) {
        Row(modifier = Modifier) {
            Text(
                text = chipState.name,
                fontSize= 9.sp,
                modifier = Modifier
                    .padding(8.dp)
            )

            Image(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clickable {
                        //chipState.isSelected.value = false
                        onClickChip(chipState)
                        //selected.value = selectedItem.contains(chipState)
                    },
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}