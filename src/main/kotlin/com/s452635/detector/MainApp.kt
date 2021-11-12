package com.s452635.detector

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application

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