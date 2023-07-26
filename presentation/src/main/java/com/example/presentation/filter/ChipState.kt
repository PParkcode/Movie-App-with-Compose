package com.example.presentation.filter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ChipState(
    val name:String,
    val category:Int,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)