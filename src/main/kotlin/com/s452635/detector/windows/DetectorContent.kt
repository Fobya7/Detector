package com.s452635.detector.windows

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.s452635.detector.detecting.DetectorValues
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
    val menuOpenGen : () -> Unit,
    val detValues : MutableState<DetectorValues>
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
    DetectorContent( detectorState )
}

@Composable
fun DetectorContent(
    detectorState : DetectorState
) {
    MainColumn {

        PatheticBorder { Row {
            StereoButton(
                text = "START",
                buttonPos = ButtonPosition.Left,
                buttonSize = ButtonSize.Biggie,
                isEnabled = detectorState.isStartEnabled,
                onClickChecked = detectorState.onClickStartChecked,
                onClickUnchecked = detectorState.onClickStartUnchecked
            )
            Spacer( Modifier.width( 5.dp ) )
            StereoButton(
                text = "INIT",
                buttonPos = ButtonPosition.Right,
                buttonSize = ButtonSize.Biggie,
                isEnabled = detectorState.isInitEnabled,
                onClickChecked = detectorState.onClickInitChecked,
                onClickUnchecked = detectorState.onClickInitUnchecked
            )
            Spacer( Modifier.width( 5.dp ) )
            Column()
            {
                LabeledButton(
                    buttonText = "HL Input",
                    onClick = detectorState.onClickHL,
                    label = detectorState.labelHL,
                    isEnabled = detectorState.isHLEnabled,
                    buttonFraction = 0.5F
                    )
                Spacer( Modifier.height( 5.dp ) )
                LabeledButton(
                    buttonText = "Gear System",
                    onClick = detectorState.onClickGS,
                    label = detectorState.labelGS,
                    isEnabled = detectorState.isGSEnabled,
                    buttonFraction = 0.5F
                    )
            }
        } }

        Spacer( Modifier.height( 10.dp ) )
        DippingField(
            detectorState.detValues.value.buildString()
        )

    }
}