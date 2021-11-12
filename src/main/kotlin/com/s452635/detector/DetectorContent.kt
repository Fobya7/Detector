package com.s452635.detector

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

class DetectorState (
    var isAppBusy: MutableState<Boolean>
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
    DetectorContent()
}

@Composable
fun DetectorContent()
{
    MainColumn {
        Text( "it's the detector" )
    }
}