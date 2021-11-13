package com.s452635.detector

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

class DetectorState (
    val isAppBusy: MutableState<Boolean>,

    val startButton : () -> Unit,
    val hlInputButton : () -> Unit,
    val hlInputLabel: MutableState<String>,
    val gsButton: () -> Unit,
    val gsButtonEnabled: MutableState<Boolean>,
    val gsLabel: MutableState<String>
)

@Composable
fun ApplicationScope.DetectorWindow(
    detectorState : DetectorState
) = Window (
    onCloseRequest = ::exitApplication,
    title = "GearSym2000",
    enabled = !detectorState.isAppBusy.value,
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize.Unspecified
        )
    )
{
    DetectorContent(
        startButton = detectorState.startButton,
        hlInputButton = detectorState.hlInputButton,
        hlInputLabel = detectorState.hlInputLabel,
        gsButton = detectorState.gsButton,
        gsButtonEnabled = detectorState.gsButtonEnabled,
        gsLabel = detectorState.gsLabel
    )
}

@Composable
fun DetectorContent(
    startButton : () -> Unit,
    hlInputButton : () -> Unit,
    hlInputLabel : MutableState<String>,
    gsButton : () -> Unit,
    gsButtonEnabled: MutableState<Boolean>,
    gsLabel : MutableState<String>
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
                    isEnabled = gsButtonEnabled,
                    buttonFraction = 0.5F
                    )
            }
        }

    }
}