package com.s452635.detector.styling

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.ScanQueue
import kotlin.concurrent.thread

@ExperimentalFoundationApi
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
        LabeledField2(
            label = "HL Input",
            value = rad2,
            isEnabled = false
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

@Composable
fun scanBoxTest()
{
    val one = ScanQueue( startingList = listOf( "H", "L", "H", "L", "H", "L", "H", "L", "H", "L", "H", "L", "H", "L" ) )
    thread {
        while( !one.isBlocked() )
        {
            one.nextItem()
            Thread.sleep( 500 )
        }
    }

    MainColumn {
        ScanRow( mutableStateOf(one) )
    }
}

@Composable
fun stereoButtonTest()
{
    MainColumn {
        Row {
            StereoButton(
                text = "HEWWO",
                buttonPos = ButtonPosition.Left,
                buttonSize = ButtonSize.Biggie
                )
            Spacer( Modifier.width( 5.dp ) )
            StereoButton(
                text = "FOLLOW",
                isEnabled = mutableStateOf( false ),
                buttonPos = ButtonPosition.Right,
                buttonSize = ButtonSize.Biggie
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
        stereoButtonTest()
    }
}