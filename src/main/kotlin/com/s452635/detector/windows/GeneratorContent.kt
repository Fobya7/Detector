package com.s452635.detector.windows

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.s452635.detector.detecting.FileValues
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.styling.*

class GeneratorState(
    val isEnabled : MutableState<Boolean>,
    val isVisible : MutableState<Boolean>,
    val onClose : () -> Unit,
    val isAlone : MutableState<Boolean>,
    val genValues : MutableState<GenValues>,
    val onClickGenerate : () -> Unit,
    val onClickGS : () -> Unit,
    val onClickDir : () -> Unit,
    val fileValues : FileValues,
)

@ExperimentalFoundationApi
@Composable
fun GeneratorWindow(
    generatorState : GeneratorState
) {
    Window(
        onCloseRequest = generatorState.onClose,
        title = "The Generator",
        enabled = generatorState.isEnabled.value,
        visible = generatorState.isVisible.value,
        state = rememberWindowState(
            position = WindowPosition( 360.dp, 400.dp ),
            width = Dp.Unspecified, height = Dp.Unspecified
        )
    )
    {
        if( generatorState.isAlone.value ) GeneratorContentStandalone( generatorState )
        else GeneratorContent( generatorState )
    }
}

@Composable
fun GeneratorContent(
    generatorState : GeneratorState
) {
    MainColumn {
        DippingField( generatorState.genValues.value.buildString() )
    }
}

@ExperimentalFoundationApi
@Composable
fun GeneratorContentStandalone(
    generatorState : GeneratorState
) {
    val linesField = NumberFieldManager(
        label = "No. of Lines",
        tooltip = "How many lines of HL to generate?\nBetween 1 and 1000.",
        correctionChecking = { it.number in 1..1000 }
    )

    val canGenerate = mutableStateOf( false )
    fun checkIfCanGenerate()
    {
        canGenerate.value =
            ( generatorState.fileValues.isTXTChecked.value ||
              generatorState.fileValues.isHLChecked.value ) &&
            generatorState.fileValues.fileName.value.isNotBlank() &&
            linesField.isCorrect &&
            generatorState.fileValues.gs.value != null
    }

    MainColumn {
        LabeledButton(
            buttonText = "Gear System",
            onClick = generatorState.onClickGS,
            label = mutableStateOf("none"),
            isEnabled = mutableStateOf(true),
            buttonFraction = 0.5F
            )
        /*
        Spacer( Modifier.height( 5.dp ) )
        LabeledButton(
            buttonText = "Directory",
            buttonFraction = 0.5F,
            onClick = generatorState.onClickDir
            )
        */

        Spacer( Modifier.height( 15.dp ) )
        LabeledField(
            label = "File Name",
            value = generatorState.fileValues.fileName,
            labelFraction = 0.5F,
            correctionChecking = { generatorState.fileValues.fileName.value.isNotBlank() }
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label = linesField.label,
            tooltipText = linesField.tooltip,
            value = linesField.field,
            onValueChange = {
                linesField.onValueChange()
                generatorState.fileValues.fileLines.value = linesField.number
                checkIfCanGenerate()
                },
            correctionChecking = { linesField.isCorrect },
            labelFraction = 0.5F
            )

        Spacer( Modifier.height( 15.dp ) )
        Row {
            StereoButton(
                text = ".TXT",
                buttonPos = ButtonPosition.Left,
                isChecked = generatorState.fileValues.isTXTChecked,
                onClickChecked = { checkIfCanGenerate() },
                onClickUnchecked = { checkIfCanGenerate() }
                )
            Spacer( Modifier.width( 5.dp ) )
            MyButton(
                buttonText = "GENERATE",
                buttonPos = ButtonPosition.Center,
                isEnabled = canGenerate,
                onClick = generatorState.onClickGenerate
                )
            Spacer( Modifier.width( 5.dp ) )
            StereoButton(
                text = ".HL",
                buttonPos = ButtonPosition.Right,
                isChecked = generatorState.fileValues.isHLChecked,
                onClickChecked = { checkIfCanGenerate() },
                onClickUnchecked = { checkIfCanGenerate() }
                )
        }
    }
}
