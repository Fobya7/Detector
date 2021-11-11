package com.s452635.detector

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

fun main() = application {
    val applicationState = remember { ApplicationState() }
    DetectorWindow( applicationState.detector.value )
    DialogWindow( applicationState.dialog.value )
}

private class ApplicationState
{
    var isDialogOpen = mutableStateOf( false )
    val detector = mutableStateOf( detectorState() )
    val dialog = mutableStateOf( dialogState() )

    fun closeDialog() { isDialogOpen.value = false }
    fun openDialog() { isDialogOpen.value = true }

    private fun detectorState(
    ) = DetectorState(
        dialogOpt = ::openDialog
    )

    private fun dialogState(
    ) = DialogState(
        isVisible = isDialogOpen,
        close = ::closeDialog
    )

}

private class DetectorState(
    val dialogOpt: () -> Unit
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
    DetectorContent( detectorState.dialogOpt )
}

private class DialogState(
    var isVisible: MutableState<Boolean>,
    val close: () -> Unit
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
    DialogContent()
}