package com.s452635.detector.windows

import com.s452635.detector.HlOption
import java.io.File
import java.nio.file.Paths
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

val dataFile = File( File("src/main/resources/Data.txt").absolutePath )
var currentWorkingDirectory: File = pathInitCWD()
fun pathInitCWD() : File
{
    val savedWorkingDirectory = Paths.get((dataFile.readText())).toFile()

    if( !savedWorkingDirectory.isDirectory )
    {
        return File( System.getProperty("user.home") )
    }

    return savedWorkingDirectory
}
fun pathSaveCWD( file: File )
{
    dataFile.writeText( file.path.toString() )
}

val chooserHL = JFileChooser()
val chooserGear = JFileChooser()
fun initFileChoosers()
{
    chooserHL.currentDirectory = currentWorkingDirectory
    chooserGear.currentDirectory = currentWorkingDirectory
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
        return Pair(HlOption.LiveGeneration, null )
    }

    if( n == 0 )
    {
        chooserHL.showOpenDialog( null )
        pathSaveCWD( chooserHL.currentDirectory )
        val returnFile = chooserHL.selectedFile
        returnFile?.let {

            if( it.name.endsWith( ".txt" ) )
            {
                return Pair(HlOption.FromTxtFile, returnFile )
            }

            if( it.name.endsWith( ".hl" ) )
            {
                return Pair(HlOption.FromHlFile, returnFile )
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