package com.s452635.detector

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

// components

// tests

@Composable
fun componentTest()
{
    val rad1 = mutableStateOf("")
    val rad2 = mutableStateOf("unchangeable")

    MainColumn {
        LabeledButton(
            buttonText = "is it",
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledButton(
            buttonText = "blue",
            isEnabled =  mutableStateOf( false )
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label ="Gear System",
            value = rad1
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label = "HL Input",
            value = rad2,
            isEnabled = mutableStateOf(false)
            )
        Spacer( Modifier.height( 15.dp ) )
        Row(
            Modifier.fillMaxWidth()
        )
        {
            MyButton(
                buttonText = "left",
                buttonPos = ButtonPosition.Left
                )
            MyButton(
                buttonText = "center",
                buttonPos = ButtonPosition.Center
                )
            MyButton(
                buttonText = "right",
                buttonPos = ButtonPosition.Right
                )
            Spacer( Modifier.weight(1F) )
            MyButton(
                buttonText = "lonely",
                buttonPos = ButtonPosition.Lonely
                )
        }
    }
}

@Composable
fun formStyle(
    isEnabled : MutableState<Boolean> = mutableStateOf( true )
) {

}

fun main() = application {
    Window (
        onCloseRequest = ::exitApplication,
        title = "???",
        state = WindowState(
            position = WindowPosition( Alignment.Center ),
            size = DpSize.Unspecified
            )
        )
    {
        formStyle()
    }
}