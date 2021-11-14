package com.s452635.detector.windows

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.s452635.detector.MainColumn

class GeneratorState(
    var isAppBusy : MutableState<Boolean>,
    var isOpen : MutableState<Boolean>
)

@Composable
fun GeneratorWindow(
    generatorState : GeneratorState
) = Window (
    onCloseRequest = {},
    title = "The Generator",
    enabled = !generatorState.isAppBusy.value,
    visible = generatorState.isOpen.value,
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize.Unspecified
        )
    )
{
    GeneratorContent()
}

@Composable
fun GeneratorContent()
{
    MainColumn {
        Text( "Hey Johnny-boy~" )
    }
}
