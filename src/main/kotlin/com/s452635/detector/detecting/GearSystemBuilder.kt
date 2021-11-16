package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// TODO : mutating?
class GearSystemBuilder
{
    companion object
    {
        const val toothAmountTooltip : String = "tooth amount"
        const val diameterTooltip : String = "diameter"
    }

    private var toothAmount : Int? = 0
    private var diameter : Int? = null

    // inputables
    val toothAmountField : MutableState<String> = mutableStateOf( "" )
    val diameterField : MutableState<String> = mutableStateOf( "" )
    val detectorTickField: MutableState<String> = mutableStateOf( "" )

    // calculables
    private val undefinedConst = "N/A"
    // val maxAGSField = MutableState<String> = getMaxAGS().
    fun getMaxAGS() : MutableState<String>
    {
        if( !isGearSystemCorrect() )
        {
            return mutableStateOf(undefinedConst)
        }

        return mutableStateOf(GearSystem.calcMaxAGS( toothAmount!!, diameter!! ).toString())
    }
    fun getAreaAngle() : MutableState<String>
    {
        if( !isGearSystemCorrect() )
        {
            return mutableStateOf( undefinedConst )
        }

        return mutableStateOf("")
    }

    fun isToothAmountFieldCorrect() : Boolean
    {
        return true
    }
    fun isDiameterFieldCorrect() : Boolean
    {
        // TODO : actual checking

        if( !diameterField.value.matches( Regex("\\d++") ) )
        {
            diameter = null
            return false
        }

        diameter = diameterField.value.toInt()
        return diameter == 2
    }
    fun isGearSystemCorrect() : Boolean
    {
        if( diameter == null ) { return false }
        if( toothAmount == null ) { return false }

        return true
    }

    fun getGearSystem() : GearSystem?
    {
        if( !isGearSystemCorrect() ) { return null }

        return GearSystem( toothAmount!!, diameter!! )
    }
}

data class GearSystem(
    val toothAmount : Int = 0,
    val diameter : Int = 0,
    val detectorTick : Int = 0,
)
{
    val maxAGS : Int = calcMaxAGS( toothAmount, diameter )

    companion object
    {
        fun calcMaxAGS( toothAmount : Int, diameter : Int ) : Int
        {
            return 3
        }
    }
}
