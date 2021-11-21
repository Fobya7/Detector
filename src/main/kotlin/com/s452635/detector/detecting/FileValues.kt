package com.s452635.detector.detecting

import androidx.compose.runtime.mutableStateOf

class FileValues
{
    val fileName = mutableStateOf( "" )
    val isTXTChecked = mutableStateOf( false )
    val isHLChecked = mutableStateOf( false )

}