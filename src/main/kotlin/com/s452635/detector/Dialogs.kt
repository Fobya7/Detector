package com.s452635.detector

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

val chooserHL = JFileChooser()
val chooserGear = JFileChooser()
fun initFileChoosers()
{
    chooserHL.fileFilter = FileNameExtensionFilter("Acceptable", "txt", "hl")
    chooserGear.fileFilter = FileNameExtensionFilter("Acceptable", "gear" )
}

fun chooseHlInput() : Pair<HlOption, File?>?
{
    val n = JOptionPane.showOptionDialog (
        null,
        "Choose HL Input. You can either\nuse the generator or load an .hl or .txt file.",
        "HL Input",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        arrayOf( "read from file", "live generation" ),
        null
    )
    if( n == -1 ){ return null }

    if( n == 1 )
    {
        return Pair( HlOption.LiveGeneration, null )
    }

    if( n == 0 )
    {
        chooserHL.showOpenDialog( null )
        val returnFile = chooserHL.selectedFile
        returnFile?.let {

            if( it.name.endsWith( ".txt" ) )
            {
                return Pair( HlOption.FromTxtFile, returnFile )
            }

            if( it.name.endsWith( ".hl" ) )
            {
                return Pair( HlOption.FromHlFile, returnFile )
            }

            JOptionPane.showMessageDialog(
                null,
                "Wrong file type."
            )
            return null

        }
    }

    return null
}