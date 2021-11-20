package com.s452635.detector.windows

import androidx.compose.foundation.layout.Spacer
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
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.styling.LabeledButton
import com.s452635.detector.styling.MainColumn
import com.s452635.detector.styling.dippingField

class GeneratorState(
    val isAppBusy : MutableState<Boolean>,
    val isOpen : MutableState<Boolean>,
    val genValues : GenValues
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
    GeneratorContent( generatorState.genValues )
}

@Composable
fun GeneratorContent(
    genValues : GenValues
) {
    MainColumn {
        LabeledButton(
            buttonText = "Gear System",
            onClick = {},
            label = mutableStateOf("none"),
            isEnabled = mutableStateOf(true),
            buttonFraction = 0.5F
            )
        Spacer( Modifier.height( 10.dp ) )
        dippingField( genValues.buildString() )
    }
}
