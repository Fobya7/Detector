package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import com.s452635.detector.styling.MyColors
import kotlin.random.Random

enum class SpinDirection { Right, Left, Stop }
enum class SpeedType { Falling, Const, Raising }

class GenValues( private val gearSystem : GearSystem )
{
    val spinDirection : MutableState<SpinDirection> = mutableStateOf( SpinDirection.Stop )
    val speed : MutableState<Int> = mutableStateOf(2)

    fun run() {

        val thread = Thread {
            while( true ) {
                Thread.sleep( gearSystem.detectorTick.toLong() )
                speed.value += Random.nextInt( 1, 6 ) -3
            }
        }

        thread.start()
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
        appendCategory( "speed" )
        append( "${speed.value}" ); append( "\n" )
        appendCategory( "spin direction" )
        append( "${spinDirection.value}" )
    }

    fun snap() : Pair<Scan,Scan>
    {
        return Pair( Scan.H, Scan.L ) // TODO : actual values
    }
}

private class Acceleration
{
    var value : MutableState<Int> = mutableStateOf( 0 )

    fun get() : Int
    {
        return value.value
    }

    fun roll()
    {
        when( Random.nextInt( 1, 100 ) )
        {
            in 1..10  -> value.value + 1
            in 11..20 -> value.value - 1
            else -> {}
        }
    }
}

/*
    private val lastAcceleration = mutableStateOf( 0 )
    private fun rollAcceleration()
    {
        val lA = lastAcceleration.value
        when( Random.nextInt( 1, 100 ) )
        {
            in 1..10  -> if( lA > 0 )   { lastAcceleration.value - 1 }
            in 11..15 -> if( lA < 100 ) { lastAcceleration.value + 1 }
            else -> {}
        }
    }
 */