package com.s452635.detector

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import java.io.File

fun main() = application {
    val applicationState = remember { ApplicationState() }
    DetectorWindow( applicationState.detectorSt.value )
    GeneratorWindow( applicationState.generatorSt.value )
}

private class ApplicationState
{
    init
    {
        initChoosers()
    }

    var isBusy = mutableStateOf( false )
    var hlOption = mutableStateOf( HlOption.None )
    var hlFile = mutableStateOf<File?>( null )
    var gsOption = mutableStateOf( GsOption.None )

    var isGeneratorOpen = mutableStateOf( false )
    fun openGenerator() { isGeneratorOpen.value = true }
    fun closeGenerator() { isGeneratorOpen.value = false }
    fun initGenerator()
    {
        openGenerator()
        // TODO initiating generator stuff
    }

    fun hlInputChoice()
    { isBusy.value = true

        val hlInput = chooseHlInput()
        if( hlInput == null ) { isBusy.value = false; return }

        hlOption.value = hlInput.first
        hlFile.value = hlInput.second

        detectorSt.value.hlInputLabel.value = when( hlOption.value )
        {
            HlOption.LiveGeneration -> "live generator"
            HlOption.FromTxtFile, HlOption.FromHlFile -> "${hlFile.value?.name}"
            HlOption.None -> "none"
        }

        detectorSt.value.gsButtonEnabled.value = hlOption.value != HlOption.FromHlFile
        if( hlOption.value == HlOption.FromHlFile )
        {
            gsOption.value = GsOption.FromHlFile
            detectorSt.value.gsLabel.value = "${ hlFile.value?.name}"
        }
        if( gsOption.value == GsOption.FromHlFile && hlOption.value != HlOption.FromHlFile )
        {
            gsOption.value = GsOption.None
            detectorSt.value.gsLabel.value = "none"
        }

    isBusy.value = false }

    fun start()
    {
        // TODO : check if selected file is correct
    }

    var detectorSt = mutableStateOf( detectorState() )
    private fun detectorState() = DetectorState (
        isAppBusy = isBusy,
        startButton = ::closeGenerator,
        hlInputButton = ::hlInputChoice,
        hlInputLabel = mutableStateOf("none"),
        gsButton = {},
        gsButtonEnabled = mutableStateOf(true),
        gsLabel = mutableStateOf("none")
    )

    var generatorSt = mutableStateOf( generatorState() )
    private fun generatorState() = GeneratorState (
        isAppBusy = isBusy,
        isOpen = isGeneratorOpen
    )
}