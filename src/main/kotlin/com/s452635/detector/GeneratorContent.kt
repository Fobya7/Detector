package com.s452635.detector

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun GeneratorContent(randomInt : MutableState<Int> )
{
    MaterialTheme {
        Text( randomInt.value.toString() )
    }
}
