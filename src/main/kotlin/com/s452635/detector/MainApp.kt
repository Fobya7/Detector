package com.s452635.detector

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import com.s452635.detector.windows.chooseHlInput
import com.s452635.detector.windows.initFileChoosers
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
        initFileChoosers()
    }

    // region valuable variables

    var hlFile = mutableStateOf<File?>( null )
    var gsFile = mutableStateOf<File?>( null )

    // endregion

    // region program management

    var isBusy = mutableStateOf( false )
    fun makeBusy() { isBusy.value = true }
    fun makeNotBusy() { isBusy.value = false }

    var programOption = mutableStateOf( ProgramOption.Init )
    var hlOption = mutableStateOf( HlOption.None )
    var gsOption = mutableStateOf( GsOption.None )

    var isGearFormEditable = mutableStateOf( true )
    var isGearFormVisible = mutableStateOf( false )
    fun hideGearForm() { isGearFormVisible.value = false }
    fun showGearForm()
    {
        isGearFormEditable.value = when( programOption.value )
        {
            ProgramOption.Init -> true
            else -> false
        }

        isGearFormVisible.value = true
    }

    var isGeneratorVisible = mutableStateOf( false )
    fun showGenerator() { isGeneratorVisible.value = true }
    fun hideGenerator() { isGeneratorVisible.value = false }
    fun initGenerator()
    {
        showGenerator()
        // TODO initiating generator stuff
    }

    // endregion

    // region window functions

    var canStart = mutableStateOf( false )
    fun checkIfCanStart()
    {
        canStart.value = hlOption.value != HlOption.None && gsOption.value != GsOption.None
    }
    fun startButton()
    {
        // TODO : check if selected file is correct
    }

    fun hlButton()
    {
        fun hlInputChoice()
        {
            val hlInput = chooseHlInput() ?: return
            hlOption.value = hlInput.first
            hlFile.value = hlInput.second

            updateGsButton()
            updateButtonLabels()
            checkIfCanStart()
        }

        when( programOption.value )
        {
            ProgramOption.Init ->
            {
                makeBusy()
                hlInputChoice()
                makeNotBusy()
            }
            else -> {}
        }
    }

    var isGsButtonEnabled = mutableStateOf( true )
    fun updateGsButton()
    {
        isGsButtonEnabled.value = hlOption.value != HlOption.FromHlFile
    }
    fun gsButton()
    {
        when( programOption.value )
        {
            ProgramOption.Init ->
            {
                makeBusy()
                showGearForm()
                makeNotBusy()
                // TODO : it's not a dialog, make not busy by click
            }
            else -> {}
        }
    }

    var gsLabel = mutableStateOf( "none" )
    var hlLabel = mutableStateOf( "none" )
    fun updateButtonLabels()
    {
        fun updateHlLabel()
        {
            hlLabel.value = when( hlOption.value )
            {
                HlOption.None -> "none"
                HlOption.FromTxtFile, HlOption.FromHlFile -> hlFile.value!!.name
                HlOption.LiveGeneration -> "live generation"
            }
        }

        fun updateGsLabel()
        {
            if( gsOption.value == GsOption.FromHlFile && hlOption.value != HlOption.FromHlFile )
            {
                gsOption.value = GsOption.None
            }
            if( hlOption.value == HlOption.FromHlFile )
            {
                gsOption.value = GsOption.FromHlFile
            }

            gsLabel.value = when( gsOption.value )
            {
                GsOption.None -> "none"
                GsOption.FromHlFile -> hlFile.value!!.name
                GsOption.Custom -> "custom system"
                GsOption.FromGearFile -> gsFile.value!!.name
            }
        }

        updateHlLabel()
        updateGsLabel()
    }

    var detectorSt = mutableStateOf( detectorState() )
    private fun detectorState() = DetectorState (
        isAppBusy = isBusy,
        startButton = ::hideGenerator,
        hlButton = ::hlButton,
        gsButton = ::gsButton,
        gsButtonEnabled = isGsButtonEnabled,
        startButtonEnabled = canStart,
        hlLabel = hlLabel,
        gsLabel = gsLabel,
    )

    var generatorSt = mutableStateOf( generatorState() )
    private fun generatorState() = GeneratorState (
        isAppBusy = isBusy,
        isOpen = isGeneratorVisible
    )

    // endregion

}

enum class HlOption { None, LiveGeneration, FromTxtFile, FromHlFile }
enum class GsOption { None, FromHlFile, FromGearFile, Custom }
enum class ProgramOption { Init, Working }