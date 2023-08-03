package com.example.presentation.filter

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.GenreHash
import com.example.domain.model.MovieCover
import com.example.domain.usecase.GetFilterMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getFilterMoviesUseCase: GetFilterMoviesUseCase
):ViewModel() {

    var selectedChip = mutableStateListOf<ChipState>() // 이 방식은 전체를 재구성 중복을 감지 못함
    var filterMovieResult : MutableList<MovieCover> by mutableStateOf(mutableListOf())

    //var selectedChip  by mutableStateOf(mutableListOf<ChipState>()) //이거는 칩 하나만 재구성 대신 중복 감지 가능 -> 이거 사용할거

    //var selectedChip = mutableStateListOf<ChipState>()

    //var selectedChip = MutableStateFlow<MutableList<ChipState>>(mutableListOf())
    //var selectedChip : MutableList<ChipState> by mutableStateOf(mutableListOf())

    //val selectedChip = mutableStateListOf<ChipState>()
    //val selectedChip :MutableList<ChipState> by mutableStateOf(mutableListOf())

    /**
     *  var selectedChip = mutableStateListOf<ChipState>()
     *  val selectedChip :MutableList<ChipState> by mutableStateOf(mutableListOf())
     *  위 두 방식 차이 이해할 것.
     *  위 방식대로 하면 정상 동작
     *  아래 방식은 selectedItem의 재구성을 하지 않음
     */

    private fun addChip(chip: ChipState) {
        chip.isSelected.value = true
        selectedChip.add(chip)
    }
    private fun deleteChip(chip: ChipState) {
        chip.isSelected.value = false
        selectedChip.remove(chip)
    }

    fun clickChip(chip: ChipState) {
        Log.d("park","chip: ${chip.name} is clicked")
        if (selectedChip.contains(chip)){
            Log.d("park","증복된 칩")
            deleteChip(chip)
        }

        else {
            val size = selectedChip.size

            if(chip.category == 2 ){
                for(i in 0 until size) {
                    if(selectedChip[i].category ==2) {
                        deleteChip(selectedChip[i])
                        break
                    }
                }
            }

            else {
                for(i in 0 until size) {
                    if(selectedChip[i].name == chip.name){
                        deleteChip(selectedChip[i])
                        break
                    }
                }
            }
            addChip(chip)
        }
    }
    private fun makeQuery():MutableList<String> {
        val genreHash = GenreHash()
        var withGenres: String =""
        var stars: String =""
        var withoutGenres:String =""
        val query = mutableListOf<String>()
        for(chip in selectedChip) {
            when(chip.category) {
                1 -> {
                    withGenres += genreHash.getValue(chip.name)
                    withGenres +="|"
                }
                2 -> {
                    stars = chip.name
                }
                3 -> {
                    withoutGenres += genreHash.getValue(chip.name)
                    withoutGenres+="|"
                }
                else-> {
                }
            }

        }
        query.add(withGenres)
        query.add(stars)
        query.add(withoutGenres)
        return query
    }
    suspend fun getFilterMovies() {
        val query = makeQuery()
        Log.d("park","query: $query")
        filterMovieResult = getFilterMoviesUseCase.invoke(query[0],query[1],query[2]).toMutableList()
        Log.d("park", "filterResult: $filterMovieResult")
    }

}