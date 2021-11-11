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
    DialogWindow( applicationState.dialog.value )

}

private class ApplicationState
{
    // variables
    var isDialogOpen = mutableStateOf( false )
    fun closeDialog() { isDialogOpen.value = false }
    fun openDialog() { isDialogOpen.value = true }
    var itChanges = mutableStateOf( 0 )

    // windows
    val detector = mutableStateOf( detectorState() )
    val dialog = mutableStateOf( dialogState() )
    val isFileChooserVisible = mutableStateOf( false )

    fun readFromFile()
    {
        // TODO : disable main window while picking file
        filePickingDialog()
        // TODO : think
        // do not import entire file - read it line by line
        // cause it could be huge
    }

    private fun detectorState(
    ) = DetectorState(
        readFromFile = ::readFromFile
    )

    private fun dialogState(
    ) = DialogState(
        isVisible = isDialogOpen,
        close = ::closeDialog,
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
    val readFromFile: () -> Unit
)

@Composable
private fun ApplicationScope.DetectorWindow (
    detectorState: DetectorState
) = Window (
    onCloseRequest = ::exitApplication,
    title = "GearSym2000",
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize.Unspecified
        )
    )
{
    DetectorContent(
        readFromFile = detectorState.readFromFile
    )
}

private class DialogState(
    var isVisible: MutableState<Boolean>,
    val close: () -> Unit,
    var randomInt : MutableState<Int>
)

@Composable
private fun DialogWindow (
    dialogState : DialogState
) = Window (
    visible = dialogState.isVisible.value,
    onCloseRequest = { dialogState.close() },
    title = "dialog",
    state = WindowState(
        position = WindowPosition( Alignment.Center ),
        size = DpSize( 300.dp, 200.dp )
        )
    )
{
    DialogContent( dialogState.randomInt )
}