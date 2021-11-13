package com.s452635.detector

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun detectorStyle(
    startButton : () -> Unit = {},
    hlInputButton : () -> Unit = {},
    hlInputLabel : MutableState<String> = mutableStateOf("none"),
    gsButton : () -> Unit = {},
    gsLabel : MutableState<String> = mutableStateOf("none")
) {
    MainColumn {
        Row(
            Modifier
                .height( IntrinsicSize.Max )
                .border( 1.dp, MyColors.DisabledBack )
                .padding( 10.dp )
        )
        {
            Button(
                content = { Text( "START", fontSize = 18.sp ) },
                onClick = startButton,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MyColors.Primary,
                    contentColor = Color.White
                    ),
                shape = MyShapes.Uneven,
                modifier = Modifier
                    .fillMaxHeight()
                    .width( 100.dp )
            )
            Spacer( Modifier.width( 5.dp ) )
            Column()
            {
                LabeledButton(
                    buttonText = "HL Input",
                    onClick = hlInputButton,
                    label = hlInputLabel,
                    buttonFraction = 0.5F
                    )
                Spacer( Modifier.height( 5.dp ) )
                LabeledButton(
                    buttonText = "Gear System",
                    onClick = gsButton,
                    label = gsLabel,
                    buttonFraction = 0.5F
                    )
            }
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
        detectorStyle()
    }
}