package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import com.s452635.detector.styling.MyColors
import com.s452635.detector.styling.doublePrint
import java.lang.Thread.sleep
import kotlin.random.Random
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt

private val defaultGearSystem = GearSystem (
    toothAmount = 12,
    diameter = 10,
    detectorTick = 1000
    )
class GenValues( private val gearSystem : GearSystem = defaultGearSystem )
{
    private val speed = SpeedAcc()
    private val scA = Sc( 40 )
    private val scB = Sc( 0 )

    fun firstStep()
    {
        sleep( 500 )
        step()
    }
    fun step()
    {
        speed.newVel()
        scA.bumpBy( speed.getSpeed() )
        scB.bumpBy( speed.getSpeed() )
    }

    fun run() = Thread { while( true ) {
    Thread.sleep( gearSystem.detectorTick.toLong() )

        step()

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

        appendCategory( "scA / scB" )
        append(    "${scA.areaType.value}(${scA.areaProc.value/100.0})" +
                " / ${scB.areaType.value}(${scB.areaProc.value/100.0})" ); append( "\n" )
        append( "\n" )

        appendCategory( "speed" )
        append(doublePrint(gearSystem.maxAGS * speed.velValue.value / 100.0)); append( "\n" )
        appendCategory( "speed ratio" )
        append( "${speed.velValue.value / 100.0}" ); append( "\n" )
        appendCategory( "spin direction" )
        append( "${speed.velType.value}" ); append( "\n" )
        append( "\n" )

        appendCategory( "acceleration" )
        append( "${speed.accValue.value / 100.0}" ); append( "\n" )
        appendCategory( "acceleration type" )
        append( "${speed.accType.value}" )
    }

    fun snap() : Pair<Area,Area>
    {
        return Pair( scA.areaType.value, scB.areaType.value )
    }
    fun snapStr() : String
    {
        val snap = snap()
        return "${snap.first}${snap.second}"
    }
}

enum class Area
{
    H { override fun flip() : Area { return L } },
    L { override fun flip() : Area { return H } };

    abstract fun flip() : Area
}
class Sc(
    startingProc : Int,
    startingArea : Area = Area.H
    )
{
    val areaProc : MutableState<Int> = mutableStateOf( startingProc )
    val areaType : MutableState<Area> = mutableStateOf( startingArea )

    fun bumpBy( step : Int )
    {
        areaProc.value += step

        if( areaProc.value > 100 )
        {
            areaProc.value -= 100
            areaType.value = areaType.value.flip()
        } else
        if( areaProc.value < 0 )
        {
            areaProc.value += 100
            areaType.value = areaType.value.flip()
        }
    }
}

enum class SpinDir { Right, Left, Stop }
enum class AccType { Slowing, Speeding, None }
class SpeedAcc
{
    val accType : MutableState<AccType> = mutableStateOf( AccType.None )
        // values between 0 and 10
        private val maxAcc = 10
    val accValue : MutableState<Int> = mutableStateOf(0)
    fun getAccValue() : Double { return accValue.value / 100.0 }

    val velType : MutableState<SpinDir> = mutableStateOf(SpinDir.Stop)
        // values between 0 and 90
    val velValue : MutableState<Int> = mutableStateOf(0)
    private val maxVel = 90
    fun getVelValue() : Double { return velValue.value / 100.0 }

    private fun rollAccChange() : Int
    {
        return when( nextInt( 0, 100 ) )
        {
            in 10..39 -> 1
            in 40..50 -> 2
            else -> 0
        }
    }
    private fun rollAccType()
    {
        accType.value = when( Random.nextBoolean() )
        {
            true ->  AccType.Speeding
            false -> AccType.Slowing
        }
    }
    private fun nextAcc()
    {
        val accChange = rollAccChange()
        if( accChange == 0 ) { return }

        // region extreme cases

        if( accValue.value == maxAcc )
        {
            accValue.value -= accChange
            return
        }

        if( accValue.value == 0 )
        {
            accValue.value += accChange
            rollAccType()
            return
        }

        // endregion

        when( Random.nextBoolean() )
        {
            true ->
            {
                accValue.value += accChange
                if( accValue.value > maxAcc ) { accValue.value = maxAcc }
            }
            false ->
            {
                accValue.value -= accChange
                if( accValue.value < 0 ) { accValue.value = 0 }
            }
        }

        if( accValue.value == 0 )
        {
            accType.value = AccType.None
        }
    }

    fun newVel()
    {
        nextAcc()
        when( accType.value )
        {
            AccType.Speeding ->
            {
                velValue.value += accValue.value
                if( velValue.value > maxVel ) { velValue.value = maxVel }
            }
            AccType.Slowing ->
            {
                velValue.value -= accValue.value
                if( velValue.value < 0 ) { velValue.value = 0 }
            }
            else -> {}
        }

        if( velValue.value == maxVel || velValue.value == 0 )
        {
            accType.value = AccType.None
            accValue.value = 0
        }

        if( velValue.value == 0 )
        {
             velType.value = SpinDir.Stop
        }
        if( velValue.value != 0 && velType.value == SpinDir.Stop )
        {
            velType.value = when( nextBoolean() )
            {
                true -> SpinDir.Right
                false -> SpinDir.Left
            }
        }
    }

    fun getSpeed() : Int
    {
        return when( velType.value )
        {
            SpinDir.Left -> + velValue.value
            SpinDir.Right -> - velValue.value
            else -> 0
        }
    }
}