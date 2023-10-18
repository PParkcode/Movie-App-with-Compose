package com.example.presentation.myPage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.myPage.ui.theme.ComposeMovieTheme
import dagger.hilt.android.lifecycle.HiltViewModel




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
) {


}
@Preview(showSystemUi = true)
@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier
){
    Column {
        Text(text = "내가 찜한 목록", fontWeight = FontWeight.Bold)
        MyMovieList()
    }
}

@Composable
fun MyMovieList() {

}
