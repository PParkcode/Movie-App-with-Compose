package com.example.presentation.filter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
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
                    BackDropScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackDropScreen(viewModel: FilterViewModel = hiltViewModel()) {
    Log.d("park","BackDropScreen 재구성")
    clearSelectedItem(viewModel)

    val stars = remember { mutableStateListOf( ChipState("3.0", 2),
        ChipState("5.0", 2),
        ChipState("7.0", 2),
        ChipState("8.0", 2)) }

    val genres = GenreHash().getAllKeys()
    val favoriteGenreList = mutableListOf<ChipState>()
    val hateGenreList = mutableListOf<ChipState>()

    for (item in genres) {
        favoriteGenreList.add(ChipState(item, 1))
        hateGenreList.add(ChipState(item, 3))
    }

    BackdropScaffold(
        appBar = { },
        backLayerContent = { BackScreen() },
        frontLayerContent = { FrontScreen(viewModel,favoriteGenreList = favoriteGenreList,stars = stars, hateGenreList = hateGenreList) },
        //scaffoldState = rememberBackdropScaffoldState (BackdropValue.Revealed),
    ) {

    }
}

@Composable
fun BackScreen() {
    Column(Modifier.padding(16.dp)) {
        Text("Back Layer Content")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Handle button click */ }) {
            Text("Click me")
        }
    }
}



@Composable
fun FrontScreen(viewModel: FilterViewModel, favoriteGenreList:List<ChipState>,stars:List<ChipState>,hateGenreList:List<ChipState>) {

    //장르의 String 값들

    Log.d("park", "FrontScreen 재구성 ${viewModel.selectedChip.toList()}")

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
            Text(
                text = "X",
                Modifier
                    .alignByBaseline()
                    .padding(end = 11.dp), fontSize = 17.sp
            )
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
                    viewModel = viewModel,
                    selectedItem = viewModel.selectedChip,
                    onClickChip = { viewModel.clickChip(it) })
            }
            item {
                OneCategory(
                    text = "평점이 이 정도는 되야지!",
                    items = stars,
                    viewModel = viewModel,
                    selectedItem = viewModel.selectedChip,
                    onClickChip = { viewModel.clickChip(it) })
            }
            item {
                OneCategory(
                    text = "이 장르는 싫어요!",
                    items = hateGenreList,
                    viewModel = viewModel,
                    selectedItem = viewModel.selectedChip,
                    onClickChip = { viewModel.clickChip(it) })
            }
        }
        BottomStickyButton(viewModel=viewModel, selectedItem = viewModel.selectedChip, onClickChip = {}, modifier = Modifier.weight(0.2f))
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OneCategory(
    text: String,
    items: List<ChipState>,
    viewModel: FilterViewModel,
    selectedItem: List<ChipState>,
    onClickChip: (ChipState) -> Unit
) {
    Log.d("park", "OneCategory 재구성: selectedItem = ${viewModel.selectedChip.toList()}")
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
                        viewModel = viewModel,
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

@Composable
fun Chip(
    chipState: ChipState,
    modifier: Modifier = Modifier,
    onClickChip: (ChipState) -> Unit,
    selectedItem: List<ChipState>,
    viewModel: FilterViewModel
) {

    //var selected by remember { mutableStateOf(viewModel.selectedChip.contains(chipState)) }
    var selected by remember { mutableStateOf(chipState.isSelected) }

    Log.d("park","chip: ${chipState.name} 재구성 selectedItem: ${viewModel.selectedChip.toList()}")
    Log.d("park","chip: ${chipState.name} 재구성 selected: $selected")
    Log.d("park","chip 주소: ${chipState}")
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
            .background(if (selected.value) Color.Blue else Color.White)

            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
                onClickChip(chipState)
                selected.value = viewModel.selectedChip.contains(chipState)
            }



    ) {
        Text(chipState.name, color = if (selected.value) Color.White else Color.Black)
    }
}

@Composable
fun BottomChip(chipState: ChipState,modifier: Modifier) {
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
                //style = typography.body2,
                modifier = Modifier
                    .padding(8.dp)
            )

            Image(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clickable { },
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

// 여기서의 Chip은 다르게 정의할 것

@Composable
fun BottomStickyButton(modifier: Modifier, selectedItem: List<ChipState>, viewModel: FilterViewModel, onClickChip: (String) -> Unit) {
    Log.d("park","BottomStickyButton 재구성 : ${viewModel.selectedChip}")


    Column(modifier) {

        //items 매개변수를 selectedChipState에서 selectedItem 으로 변경
        LazyRow {
            items(selectedItem) { item ->
                BottomChip(chipState =item , modifier = Modifier.padding(end = 8.dp))
            }

        }
        Row(
        ) {
            TextButton(onClick = { /*TODO*/ }, Modifier.weight(0.3f)) {
                Text(text = "초기화")
            }
            Button(onClick = { /*TODO*/ }, Modifier.weight(0.7f)) {
                Text("적용", color = Color.White)
            }
        }
    }

}

fun clearSelectedItem(viewModel: FilterViewModel) {
    viewModel.selectedChip.clear()
}
@Composable
fun GreetingPreview3() {
    ComposeMovieTheme {
        BackDropScreen()
    }
}
