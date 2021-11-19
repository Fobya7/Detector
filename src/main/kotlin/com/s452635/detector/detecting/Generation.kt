package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import com.s452635.detector.styling.MyColors
import kotlin.random.Random.Default.nextInt

enum class SpinDirection { Right, Left, Stop }

class GenValues( private val gearSystem : GearSystem )
{
    private val spinDirection : MutableState<SpinDirection> = mutableStateOf( SpinDirection.Stop )
    private val speed : MutableState<Double> = mutableStateOf( 0.0 )
    private val speedRatio : MutableState<Double> = mutableStateOf(0.0)
    private val acceleration = SpeedAcc()

    fun run() = Thread { while( true ) {
    Thread.sleep( gearSystem.detectorTick.toLong() )

        acceleration.nextVel()
        speed.value = acceleration.velValue.toDouble()

        println( acceleration )

    } }.start()

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
        append( "${acceleration.velValue}" ); append( "\n" )
        appendCategory( "acceleration" )
        append( "${acceleration.accValue}" ); append( "\n" )
        appendCategory( "spin direction" )
        append( "${spinDirection.value}" )
    }

    fun snap() : Pair<Scan,Scan>
    {
        return Pair( Scan.H, Scan.L ) // TODO : actual values
    }
}

enum class AccType { Slowing, Speeding }
class SpeedAcc
{
    var accType : AccType = AccType.Speeding
    var accValue : Int = 0 // values between 0 and 0.1
    fun getAccValue() : Double { return accValue / 100.0 }

    var velType = SpinDirection.Left
    var velValue : Int = 0 // values between 0 and 0.9
    private fun getVelValue() : Double { return velValue / 100.0 }

    private fun rollAccChange() : Int
    {
        return when( nextInt( 0, 100 ) )
        {
            in 10..39 -> 1
            in 40..50 -> 2
            else -> 0
        }
    }
    fun nextVel()
    {
        val accChange = rollAccChange()
        when( accValue )
        {
            0 -> { accValue += accChange }
            10 -> { accValue -= accChange }
            else -> when( nextInt( 1, 3 ) )
            {
                1 -> { accValue += accChange }
                2 -> { accValue -= accChange }
            }
        }

        if( accValue == 0 )
        {
            accType = if( velValue == 0 ) {
                AccType.Speeding
            }
            else {
                when(nextInt(1, 3))
                {
                    1 -> AccType.Speeding
                    else -> AccType.Slowing
                }
            }
        }

        when( accType )
        {
            AccType.Speeding -> {
                velValue += accValue
                if( velValue < 0 ) { velValue = 0 }
                }
            AccType.Slowing -> {
                velValue -= accValue
                if( velValue > 90 ) { velValue = 90 }
                }
        }

        if( velValue == 0 )
        {
            velType = when( nextInt( 1, 2 ) )
            {
                1 -> SpinDirection.Right
                else -> SpinDirection.Left
            }
        }
    }

    override fun toString() : String
    {
        return "speed: ${getVelValue()}, dir: $velType"
    }
}