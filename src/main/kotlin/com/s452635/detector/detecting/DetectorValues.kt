package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import com.s452635.detector.styling.MyColors
import com.s452635.detector.styling.doublePrint

class DetectorValues (
    gearSystem : GearSystem
) {
    private val currentPair : MutableState<Pair<Area,Area>> = mutableStateOf( Pair(Area.H, Area.H) )
    private val estimatedSpeed : MutableState<Double> = mutableStateOf( 0.0 )
    private val spinDir : MutableState<SpinDir> = mutableStateOf( SpinDir.Stop )

    private val choppy1 = ChoppySpeed( gearSystem.detectorTick, gearSystem.areaAngle )
    private val choppy2 = ChoppySpeed( gearSystem.detectorTick, gearSystem.areaAngle )

    private fun guessDir( prev : Pair<Area,Area>, curr : Pair<Area,Area> ) : SpinDir
    {
        if( prev == Pair( Area.H, Area.H ) && curr == Pair( Area.H, Area.L ) )
            return SpinDir.Right
        if( prev == Pair( Area.H, Area.L ) && curr == Pair( Area.H, Area.H ) )
            return SpinDir.Right
        if( prev == Pair( Area.L, Area.H ) && curr == Pair( Area.L, Area.L ) )
            return SpinDir.Right
        if( prev == Pair( Area.L, Area.L ) && curr == Pair( Area.L, Area.H ) )
            return SpinDir.Right

        if( prev == Pair( Area.H, Area.H ) && curr == Pair( Area.L, Area.H ) )
            return SpinDir.Left
        if( prev == Pair( Area.H, Area.L ) && curr == Pair( Area.L, Area.L ) )
            return SpinDir.Left
        if( prev == Pair( Area.L, Area.H ) && curr == Pair( Area.H, Area.H ) )
            return SpinDir.Left
        if( prev == Pair( Area.L, Area.L ) && curr == Pair( Area.H, Area.L ) )
            return SpinDir.Left

        return spinDir.value
    }

    fun update( newPair : Pair<Area,Area> )
    {
        spinDir.value = guessDir( currentPair.value, newPair )
        currentPair.value = newPair

        choppy1.nextArea( newPair.first )
        choppy2.nextArea( newPair.second )
        estimatedSpeed.value = ( choppy1.speed + choppy2.speed )/2
    }

    fun buildString() = buildAnnotatedString {
        fun appendCategory(
            text : String
        ) {
            pushStyle( SpanStyle(
                color = MyColors.DisabledMain,
                fontStyle = FontStyle.Italic
                ) )
            append( "$text:" )
            pop()
            append( " " )
        }

        appendCategory( "current pair" )
        append( "${currentPair.value.first} / ${currentPair.value.second}" ); append( "\n" )
        appendCategory( "estimated speed" )
        append( doublePrint(estimatedSpeed.value) ); append( "\n" )
        appendCategory( "spin direction" )
        append( "${spinDir.value}" )
    }
}

class ChoppySpeed(
    private val tick : Int,
    private val areaAngle : Double
) {
    var rep : Int = 1
    var speed : Double = 0.0

    private fun guesstimateSpeed()
    {
        speed = areaAngle /( rep * tick )
    }

    private var lastArea : Area = Area.H
    fun nextArea( area : Area )
    {
        if( lastArea == area ) { rep += 1 }
        else { lastArea = area; rep = 1 }

        guesstimateSpeed()
    }
}