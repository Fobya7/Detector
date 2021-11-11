package com.s452635.detector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.lang.Thread.sleep

fun main() = application {
    val applicationState = remember { ApplicationState() }
    DetectorWindow( applicationState.detector.value )
    GeneratorWindow( applicationState.dialog.value )
}

private class ApplicationState
{
    // variables
    var isGeneratorOpen = mutableStateOf( false )
    fun closeGenerator() { isGeneratorOpen.value = false }
    fun openGenerator() { isGeneratorOpen.value = true }

    var isDialogOpen = mutableStateOf( false )
    var itChanges = mutableStateOf( 0 )

    // windows
    val detector = mutableStateOf( detectorState() )
    val dialog = mutableStateOf( dialogState() )

    fun readFromFile()
    {
        isDialogOpen.value = true
        filePickingDialog()
        isDialogOpen.value = false
    }

    private fun detectorState(
    ) = DetectorState(
        readFromFile = ::readFromFile,
        liveGenerate = ::openGenerator,
        isBusy = isDialogOpen
    )

    private fun dialogState(
    ) = GeneratorState(
        isVisible = isGeneratorOpen,
        close = ::closeGenerator,
        randomInt = itChanges
    )

    init
    {
        val thread1 = Thread {
            while( true )
            {
                sleep( 200 )
                itChanges.value += 1
            }
        }
        val thread2 = Thread {
            println( "2 > started" )
            sleep( 3000 )
            println( "2 > finished" )
        }
        // thread1.start()
        // thread2.start()
    }
}

private class DetectorState(
    val readFromFile: () -> Unit,
    val liveGenerate: () -> Unit,
    var isBusy: MutableState<Boolean>
)

@Composable
private fun ApplicationScope.DetectorWindow (
    detectorState: DetectorState
) = Window (
    onCloseRequest = ::exitApplication,
    title = "GearSym2000",
    enabled = !detectorState.isBusy.value,
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize.Unspecified
        )
    )
{
    DetectorContent(
        readFromFile = detectorState.readFromFile,
        liveGenerate = detectorState.liveGenerate
    )
}

private class GeneratorState(
    var isVisible: MutableState<Boolean>,
    val close: () -> Unit,
    var randomInt : MutableState<Int>
)

@Composable
private fun GeneratorWindow (
    generatorState : GeneratorState
) = Window (
    visible = generatorState.isVisible.value,
    onCloseRequest = { generatorState.close() },
    title = "Generator",
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize( 300.dp, 200.dp )
        )
    )
{
    GeneratorContent( generatorState.randomInt )
}