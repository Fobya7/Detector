package com.s452635.detector.windows

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.s452635.detector.styling.*

class DetectorState(
    val isEnabled : MutableState<Boolean>,
    val isStartEnabled : MutableState<Boolean>,
    val onClickStartChecked : () -> Unit,
    val onClickStartUnchecked : () -> Unit,
    val isInitEnabled : MutableState<Boolean>,
    val onClickInitChecked : () -> Unit,
    val onClickInitUnchecked : () -> Unit,
    val labelHL : MutableState<String>,
    val isHLEnabled : MutableState<Boolean>,
    val onClickHL : () -> Unit,
    val labelGS : MutableState<String>,
    val isGSEnabled : MutableState<Boolean>,
    val onClickGS : () -> Unit,
    val menuCanOpenGen : MutableState<Boolean>,
    val menuOpenGen : () -> Unit
)

@Composable
fun ApplicationScope.DetectorWindow(
    detectorState : DetectorState
) = Window (
    onCloseRequest = ::exitApplication,
    title = "GearSym2000",
    enabled = detectorState.isEnabled.value,
    state = rememberWindowState(
        position = WindowPosition( 320.dp, 220.dp ),
        width = Dp.Unspecified, height = Dp.Unspecified
        )
    )
{
    MenuBar {
        Menu( "File" ) {
            Item(
                text = "open generator",
                onClick = detectorState.menuOpenGen,
                enabled = detectorState.menuCanOpenGen.value
            )
        }
    }
    DetectorContent(
        isStartEnabled = detectorState.isStartEnabled,
        onClickStartChecked = detectorState.onClickStartChecked,
        onClickStartUnchecked = detectorState.onClickStartUnchecked,
        isInitEnabled = detectorState.isInitEnabled,
        onClickInitChecked = detectorState.onClickInitChecked,
        onClickInitUnchecked = detectorState.onClickInitUnchecked,
        labelHL = detectorState.labelHL,
        isHLEnabled = detectorState.isHLEnabled,
        onClickHL = detectorState.onClickHL,
        labelGS = detectorState.labelGS,
        isGSEnabled = detectorState.isGSEnabled,
        onClickGS = detectorState.onClickGS
    )
}

@Composable
fun DetectorContent(
    isStartEnabled : MutableState<Boolean>,
    onClickStartChecked : () -> Unit,
    onClickStartUnchecked : () -> Unit,
    isInitEnabled : MutableState<Boolean>,
    onClickInitChecked : () -> Unit,
    onClickInitUnchecked : () -> Unit,
    labelHL : MutableState<String>,
    isHLEnabled : MutableState<Boolean>,
    onClickHL : () -> Unit,
    labelGS : MutableState<String>,
    isGSEnabled : MutableState<Boolean>,
    onClickGS : () -> Unit
) {
    MainColumn {

        PatheticBorder { Row {
            StereoButton(
                text = "START",
                buttonPos = ButtonPosition.Left,
                buttonSize = ButtonSize.Biggie,
                isEnabled = isStartEnabled,
                onClickChecked = onClickStartChecked,
                onClickUnchecked = onClickStartUnchecked
            )
            Spacer( Modifier.width( 5.dp ) )
            StereoButton(
                text = "INIT",
                buttonPos = ButtonPosition.Right,
                buttonSize = ButtonSize.Biggie,
                isEnabled = isInitEnabled,
                onClickChecked = onClickInitChecked,
                onClickUnchecked = onClickInitUnchecked
            )
            Spacer( Modifier.width( 5.dp ) )
            Column()
            {
                LabeledButton(
                    buttonText = "HL Input",
                    onClick = onClickHL,
                    label = labelHL,
                    isEnabled = isHLEnabled,
                    buttonFraction = 0.5F
                    )
                Spacer( Modifier.height( 5.dp ) )
                LabeledButton(
                    buttonText = "Gear System",
                    onClick = onClickGS,
                    label = labelGS,
                    isEnabled = isGSEnabled,
                    buttonFraction = 0.5F
                    )
            }
        } }

    }
}