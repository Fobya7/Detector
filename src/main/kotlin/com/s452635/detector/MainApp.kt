package com.s452635.detector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.FileValues
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.windows.*
import java.io.File
import java.lang.Thread.sleep
import kotlin.math.floor

@ExperimentalFoundationApi
fun main() = application {
    val applicationState = remember { ApplicationState() }
    DetectorWindow( applicationState.detectorSt.value )
    GeneratorWindow( applicationState.generatorSt.value )
    GearFormWindow( applicationState.gearFormState.value )
}

private class ApplicationState
{
    init
    {
        initFileChoosers()
    }

    // region valuable variables

    val fileHL = mutableStateOf<File?>( null )
    val fileGS = mutableStateOf<File?>( null )
    val gearSystem = mutableStateOf( GearSystem() )
    val fileValues = FileValues()

    // endregion

    // region app synchronisation

    var isAvailable = mutableStateOf( true )
    fun makeBusy() { isAvailable.value = false }
    fun makeNotBusy() { isAvailable.value = true }

    val programState = mutableStateOf( ProgramState.Init )
    fun pauseProgram()
    {
        if( programState.value != ProgramState.Working ) { return }

        programState.value = ProgramState.Paused
    }
    fun unpauseProgram()
    {
        if( programState.value != ProgramState.Paused ) { return }

        programState.value = ProgramState.Working
    }
    fun initProgram()
    {
        if( programState.value != ProgramState.Init ) { return }

        programState.value = ProgramState.Paused
        detectorSt.value.isStartEnabled.value = true
        detectorSt.value.menuCanOpenGen.value = false
        generatorSt.value.isAlone.value = false

        mainThread()
    }
    fun abandonProgram()
    {
        if( programState.value == ProgramState.Paused ) { return }

        // TODO : ask if sure to stop
        // TODO : clearing variables, resetting states
        programState.value = ProgramState.Init
        detectorSt.value.menuCanOpenGen.value = true
        generatorSt.value.isAlone.value = true
    }
    fun programStartGenerating()
    {
        if( programState.value != ProgramState.Init ) { return }
        programState.value = ProgramState.Generating

        makeBusy()
        otherThread()
    }
    fun programStopGenerating()
    {
        if( programState.value != ProgramState.Generating ) { return }

        programState.value = ProgramState.Init

        makeNotBusy()
        hideGenerator()
        checkIfCanInitProgram()
    }

    val inputOptionHL = mutableStateOf( HLOption.None )
    val inputOptionGS = mutableStateOf( GSOption.None )

    val canInitProgram = mutableStateOf( false )
    fun checkIfCanInitProgram()
    {
        canInitProgram.value =
            inputOptionHL.value != HLOption.None &&
            inputOptionGS.value != GSOption.None &&
            programState.value == ProgramState.Init
    }

    // endregion

    // region generation

    val generator = mutableStateOf( GenValues() )
    fun mainThread()
    {
        generator.value = GenValues( gearSystem.value )

        val actualTick = floor( gearSystem.value.detectorTick / 2.0 ).toLong()
        val genThread = Thread {
            while( programState.value != ProgramState.Init )
            {
                sleep( actualTick )
                if( programState.value == ProgramState.Working )
                {
                    generator.value.step()

                    // TODO : use those values
                    sleep(actualTick)
                    println(generator.value.snap())
                }
            }
        }

        showGenerator()
        initProgram()
        genThread.start()
    }

    fun otherThread()
    {
        sleep( 2000 )
    }

    // endregion

    // region detector window

    fun onClickHL()
    {
        fun hlInputChoice()
        {
            makeBusy()
            val hlInput = chooseHlInput() ?: return
            inputOptionHL.value = hlInput.first
            fileHL.value = hlInput.second

            updateButtonLabels()
            checkIfCanInitProgram()
            makeNotBusy()
        }

        when( programState.value )
        {
            ProgramState.Init -> { hlInputChoice() }
            else -> when( inputOptionHL.value )
            {
                HLOption.LiveGeneration -> { showGenerator() }
                HLOption.FromHlFile, HLOption.FromTxtFile -> { /* TODO : show file */ }
                else -> {}
            }
        }
    }
    fun onClickGS()
    {
        when( programState.value )
        {
            ProgramState.Init ->
            {
                showGearForm()
                checkIfCanInitProgram()
            }
            else -> { /* TODO : show gs in a window */ }
        }
    }
    fun updateButtonLabels()
    {
        fun updateHlLabel()
        {
            detectorSt.value.labelHL.value = when( inputOptionHL.value )
            {
                HLOption.None -> "none"
                HLOption.FromTxtFile, HLOption.FromHlFile -> fileHL.value!!.name
                HLOption.LiveGeneration -> "live generation"
            }
        }

        fun updateGsLabel()
        {
            if( inputOptionGS.value == GSOption.FromHlFile && inputOptionHL.value != HLOption.FromHlFile )
            {
                inputOptionGS.value = GSOption.None
            }
            if( inputOptionHL.value == HLOption.FromHlFile )
            {
                inputOptionGS.value = GSOption.FromHlFile
            }

            detectorSt.value.labelGS.value = when( inputOptionGS.value )
            {
                GSOption.None -> "none"
                GSOption.FromHlFile -> fileHL.value!!.name
                GSOption.Custom -> "custom system"
                GSOption.FromGearFile -> fileGS.value!!.name
            }
        }

        fun updateGsButton()
        {
            detectorSt.value.isGSEnabled.value = inputOptionHL.value != HLOption.FromHlFile
        }

        updateHlLabel()
        updateGsLabel()
        updateGsButton()
        checkIfCanInitProgram()
    }

    val detectorSt = mutableStateOf( detectorState() )
    private fun detectorState() = DetectorState (
        isEnabled = isAvailable,
        isStartEnabled = mutableStateOf( false ),
        onClickStartChecked = ::unpauseProgram,
        onClickStartUnchecked = ::pauseProgram,
        isInitEnabled = canInitProgram,
        onClickInitChecked = ::initProgram,
        onClickInitUnchecked = ::abandonProgram,
        labelHL = mutableStateOf("none"),
        isHLEnabled = mutableStateOf( true ),
        onClickHL = ::onClickHL,
        labelGS = mutableStateOf("none"),
        isGSEnabled = mutableStateOf( true ),
        onClickGS = ::onClickGS,
        menuCanOpenGen = mutableStateOf(true),
        menuOpenGen = ::showGenerator
    )

    // endregion

    // region generator window

    fun showGenerator()
    {
        detectorSt.value.menuCanOpenGen.value = false

        generatorSt.value.isVisible.value = true
    }
    fun hideGenerator()
    {
        detectorSt.value.menuCanOpenGen.value = true

        generatorSt.value.isVisible.value = false
    }

    val generatorSt = mutableStateOf( generatorState() )
    private fun generatorState() = GeneratorState (
        isEnabled = isAvailable,
        isVisible = mutableStateOf( false ),
        isAlone = mutableStateOf( true ),
        onClose = ::hideGenerator,
        genValues = generator,
        onClickGS = ::initGearForm,
        onClickDir = ::chooseDirToSave,
        onClickGenerate = ::programStartGenerating,
        fileValues = fileValues,
    )

    // endregion

    // region gear form

    fun onClickGFAccept()
    {
        // TODO : there will be option, to load from file inside gear form
        inputOptionGS.value = GSOption.Custom
        updateButtonLabels()
        hideGearForm()
    }

    val isGearFormVisible = mutableStateOf( false )
    fun showGearForm()
    {
        makeBusy()
        isGearFormVisible.value = true
    }
    fun hideGearForm()
    {
        makeNotBusy()
        isGearFormVisible.value = false
    }

    fun initGearForm()
    {

    }

    val gearFormState = mutableStateOf( gearFormState() )
    private fun gearFormState() = GearFormState (
        isVisible = isGearFormVisible,
        gearSystem = gearSystem,
        onClose = ::hideGearForm,
        onClickAccept = ::onClickGFAccept
    )

    // endregion
}

enum class HLOption { None, LiveGeneration, FromTxtFile, FromHlFile }
enum class GSOption { None, FromHlFile, FromGearFile, Custom }
enum class ProgramState { Init, Working, Paused, Generating }