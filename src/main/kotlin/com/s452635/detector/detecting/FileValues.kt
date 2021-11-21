package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class FileValues
{
    val fileName = mutableStateOf( "" )
    val fileLines : MutableState<Int?> = mutableStateOf( null )
    val isTXTChecked = mutableStateOf( false )
    val isHLChecked = mutableStateOf( false )
    val gs : MutableState<GearSystem?> = mutableStateOf( null )
}