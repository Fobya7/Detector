package com.s452635.detector.windows

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.styling.LabeledButton
import com.s452635.detector.styling.MainColumn
import com.s452635.detector.styling.dippingField

class GeneratorState(
    val isAppBusy : MutableState<Boolean>,
    val isOpen : MutableState<Boolean>,
    val genValues : MutableState<GenValues>
)

@Composable
fun GeneratorWindow(
    generatorState : GeneratorState
) {
    Window(
        onCloseRequest = {},
        title = "The Generator",
        enabled = !generatorState.isAppBusy.value,
        visible = generatorState.isOpen.value,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            width = Dp.Unspecified, height = Dp.Unspecified
        )
    )
    {
        GeneratorContent( generatorState.genValues )
    }
}

@Composable
fun GeneratorContent(
    genValues : MutableState<GenValues>
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
        dippingField( genValues.value.buildString() )
    }
}
