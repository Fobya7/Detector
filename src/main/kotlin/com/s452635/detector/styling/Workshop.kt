package com.s452635.detector.styling

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import com.s452635.detector.detecting.MutableGearSystem

// components

// tests

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun componentTest()
{
    val rad1 = mutableStateOf("")
    val rad2 = mutableStateOf("unchangeable")
    val rad3 = mutableStateOf("just plain wrong")
    val rad4 = mutableStateOf("hewp")

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
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label = "Look Here",
            value = rad3,
            correctionChecking = { rad3.value == "rad3" },
            tooltipText = "try rad3"
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

// TODO : checking if all input correct
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun formStyle(
    isEnabled : MutableState<Boolean> = mutableStateOf( true ),
    gearSystem : MutableGearSystem = MutableGearSystem()
) {

    MainColumn {

        LabeledField(
            label = "tooth no.",
            isEnabled = isEnabled,
            value = gearSystem.toothNo
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label = "tooth length",
            isEnabled = isEnabled,
            value = gearSystem.toothLength
            )

        Spacer( Modifier.height( 15.dp ) )
        Row()
        {
            if( isEnabled.value )
            {
                MyButton(
                    buttonText = "load",
                    buttonPos = ButtonPosition.Left
                    )
                Spacer( Modifier.width( 3.dp ) )
            }
            MyButton(
                buttonText = "save",
                buttonPos = if( isEnabled.value ) ButtonPosition.Right else ButtonPosition.Lonely
                )
            Spacer( Modifier.weight(1F) )
            MyButton(
                buttonText = "accept",
                buttonPos = ButtonPosition.Lonely
                )
        }

    }

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
        formStyle( mutableStateOf( false ) )
    }
}