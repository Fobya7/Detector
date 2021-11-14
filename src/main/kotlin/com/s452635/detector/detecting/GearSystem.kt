package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class GearSystem (
    val toothNo : Int
)

data class MutableGearSystem (
    val toothNo : MutableState<String> = mutableStateOf( "" ),
    val toothLength : MutableState<String> = mutableStateOf( "" )
)

// TODO : unmutating fun