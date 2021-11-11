package com.s452635.detector

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.application
import java.io.File
import javax.swing.JFileChooser

fun filePickingDialog()
{
    val selectedFile = mutableStateOf<File?>( null )
    val chooser = JFileChooser()

    chooser.showOpenDialog( null )
    selectedFile.value = chooser.selectedFile
}

fun main() = application {
    filePickingDialog()
}