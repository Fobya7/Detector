package com.s452635.detector

import java.io.File
import javax.swing.JFileChooser

val chooser = JFileChooser()
fun filePickingDialog() : File?
{
    chooser.showOpenDialog( null )
    return chooser.selectedFile
}