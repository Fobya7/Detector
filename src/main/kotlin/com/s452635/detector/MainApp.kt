package com.s452635.detector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*

fun main() = application {
    val applicationState = remember { ApplicationState() }
    DetectorWindow( applicationState.detectorSt.value )
    GeneratorWindow( applicationState.generatorSt.value )
}

private class ApplicationState
{
    var isBusy = mutableStateOf( false )

    var isGeneratorOpen = mutableStateOf( false )
    fun openGenerator() { isGeneratorOpen.value = true }
    fun closeGenerator() { isGeneratorOpen.value = false }
    fun initGenerator()
    {
        openGenerator()
        // TODO initiating stuff
    }

    var detectorSt = mutableStateOf( detectorState() )
    private fun detectorState() = DetectorState(
        isAppBusy = isBusy
    )

    var generatorSt = mutableStateOf( generatorState() )
    private fun generatorState() = GeneratorState(
        isAppBusy = isBusy,
        isOpen = isGeneratorOpen
    )
}

private class DetectorState (
    var isAppBusy: MutableState<Boolean>
)
@Composable
private fun ApplicationScope.DetectorWindow(
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

private class GeneratorState(
    var isAppBusy : MutableState<Boolean>,
    var isOpen : MutableState<Boolean>
)
@Composable
private fun GeneratorWindow(
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