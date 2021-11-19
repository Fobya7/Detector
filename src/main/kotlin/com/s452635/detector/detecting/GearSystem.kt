package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.s452635.detector.styling.NumberFieldManager

data class GearSystem(
    val toothAmount : Int = 0,
    val diameter : Int = 0,
    val detectorTick : Int = 0,
) {
    val areaAngle : Double = calcAreaAngle( toothAmount )
    val maxAGS : Double = calcMaxAGS( areaAngle, detectorTick )
    // TODO : add precision and low speed tolerance

    private fun calcAreaAngle( toothAmount: Int ) : Double
    {
        return Math.toRadians( 360 /( 2 * toothAmount.toDouble() ) )
    }

    private fun calcMaxAGS( areaAngle : Double, detectorTick : Int ) : Double
    {
        return areaAngle / detectorTick
    }

    companion object
    {
        const val laserBias : Double = 0.9
    }
}

class GearSystemBuilder
{
    // region inputables

    val toothAmount = NumberFieldManager(
        label = "tooth amount",
        tooltip = "Between 10 and 50."
    ) {
        it.number in 10..50
    }

    val diameter = NumberFieldManager(
        label = "diameter",
        tooltip = "Between 5 and 500 cm."
    ) {
        it.number in 5..500
    }

    val detectorTick = NumberFieldManager (
        label = "detector tick",
        tooltip = "Between 500 and 2000 ms."
    ) {
        it.number!! in 500..2000
    }

    // endregion

    // region calculables

    private val undefinedConst = "N/A"
    val areaAngle : MutableState<String> = mutableStateOf(undefinedConst)
    val maxAGS : MutableState<String> = mutableStateOf(undefinedConst)

    fun updateCalculables()
    {
        if( !isGearSystemCorrect() )
        {
            areaAngle.value = undefinedConst
            maxAGS.value = undefinedConst
            return
        }
        areaAngle.value = calcAreaAngle().toString()
        maxAGS.value = calcMaxAGS().toString()
    }

    private fun calcAreaAngle() : Double
    {
        return Math.toRadians( 360 /( 2 * toothAmount.number!!.toDouble() ) )
    }
    private fun calcMaxAGS() : Double
    {
        return areaAngle.value.toDouble() / detectorTick.number!!
    }

    // endregion

    var isCorrect = mutableStateOf( false )
    private fun isGearSystemCorrect() : Boolean
    {
        isCorrect.value = toothAmount.isCorrect && diameter.isCorrect && detectorTick.isCorrect
        return isCorrect.value
    }

    fun build() : GearSystem
    {
        return GearSystem (
            toothAmount.number!!,
            diameter.number!!,
            detectorTick.number!!
            )
    }
}
