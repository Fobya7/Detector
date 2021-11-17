package com.s452635.detector.styling

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GearSystemBuilder
import com.s452635.detector.detecting.ScanQueue
import kotlin.concurrent.thread

// components

// tests

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun componentTest()
{
    val rad1 = mutableStateOf("")
    val rad2 = mutableStateOf("unchangeable")
    val rad3 = mutableStateOf("just plain wrong")
    val rad4 = mutableStateOf("hewp")

    MainColumn {
        LabeledButton(
            buttonText = "is it",
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledButton(
            buttonText = "blue",
            isEnabled =  mutableStateOf( false )
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label ="Gear System",
            value = rad1
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField2(
            label = "HL Input",
            value = rad2,
            isEnabled = false
            )
        Spacer( Modifier.height( 5.dp ) )
        LabeledField(
            label = "Look Here",
            value = rad3,
            correctionChecking = { rad3.value == "rad3" },
            tooltipText = "try rad3"
            )

        Spacer( Modifier.height( 15.dp ) )
        Row(
            Modifier.fillMaxWidth()
        )
        {
            MyButton(
                buttonText = "left",
                buttonPos = ButtonPosition.Left
                )
            MyButton(
                buttonText = "center",
                buttonPos = ButtonPosition.Center
                )
            MyButton(
                buttonText = "right",
                buttonPos = ButtonPosition.Right
                )
            Spacer( Modifier.weight(1F) )
            MyButton(
                buttonText = "lonely",
                buttonPos = ButtonPosition.Lonely
                )
        }
    }
}

@Composable
fun scanBoxTest()
{
    val one = ScanQueue( startingList = listOf( "H", "L", "H", "L", "H", "L", "H", "L", "H", "L", "H", "L", "H", "L" ) )
    thread {
        while( !one.isBlocked() )
        {
            one.nextItem()
            Thread.sleep( 500 )
        }
    }

    MainColumn {
        ScanRow( mutableStateOf(one) )
    }
}

/*
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TwinFormOld (
    isEnabled : MutableState<Boolean> = mutableStateOf( true ),
) {
    val gearSystemBuilder by remember { mutableStateOf( GearSystemBuilder() ) }

    MainColumn {
        PatheticBorder( "inputables" ) { Column {
            LabeledField(
                label = "tooth amount",
                isEnabled = isEnabled,
                value = gearSystemBuilder.toothAmountField,
                tooltipText = GearSystemBuilder.toothAmountTooltip,
                correctionChecking = { gearSystemBuilder.isToothAmountFieldCorrect() }
                )
            Spacer( Modifier.height( 5.dp ) )
            LabeledField(
                label = "diameter",
                isEnabled = isEnabled,
                value = gearSystemBuilder.diameterField,
                tooltipText = GearSystemBuilder.diameterTooltip,
                correctionChecking = { gearSystemBuilder.isDiameterFieldCorrect() }
                )
            Spacer( Modifier.height( 5.dp ) )
            /*
            LabeledField2(
                label = "detector tick",
                isEnabled = isEnabled,
                value = gearSystemBuilder.detectorTick.field,
                isEnabled = gearSystemBuilder.detectorTick.i
                ) */
        } }

        Spacer( Modifier.height( 15.dp ) )
        PatheticBorder( "calculables" ) { Column {
            LabeledField(
                label = "max AGS",
                value = gearSystemBuilder.maxAGS,
                isEnabled = mutableStateOf( false )
                )
            Spacer( Modifier.height( 5.dp ) )
            LabeledField(
                label = "area angle",
                value = mutableStateOf("0"),
                isEnabled = mutableStateOf( false )
                )
        } }

        Spacer( Modifier.height( 30.dp ) )
        Row {
            if( isEnabled.value )
            {
                MyButton(
                    buttonText = "load",
                    buttonPos = ButtonPosition.Left
                    )
                Spacer( Modifier.width( 3.dp ) )
            }
            MyButton(
                buttonText = "save",
                buttonPos = if( isEnabled.value ) ButtonPosition.Right else ButtonPosition.Lonely
                )
            Spacer( Modifier.weight(1F) )
            MyButton(
                buttonText = "accept",
                buttonPos = ButtonPosition.Lonely,
                isEnabled = gearSystemBuilder.isGearSystemCorrect()
                )
        }

    }
} */

@ExperimentalFoundationApi
@Composable
fun TwinForm(
    gearSystem : MutableState<GearSystem>
) {
    val gsb by remember { mutableStateOf( GearSystemBuilder() ) }

    @Composable
    fun ConnectedNumberField (
        nfm : NumberFieldManager
    )
    {
        LabeledField(
            label = nfm.label,
            value = nfm.field,
            tooltipText = nfm.tooltip,
            correctionChecking = { nfm.isCorrect },
            onValueChange = { nfm.onValueChange(); gsb.updateCalculables() }
            )
    }

    MainColumn {

        PatheticBorder( "inputables" ) { Column {
            ConnectedNumberField( gsb.toothAmount )
            Spacer( Modifier.height( 5.dp ) )
            ConnectedNumberField( gsb.diameter )
            Spacer( Modifier.height( 5.dp ) )
            ConnectedNumberField( gsb.detectorTick )
        } }

        Spacer( Modifier.height( 10.dp ) )
        PatheticBorder( "calculables" ) { Column {
            LabeledField(
                label = "area angle",
                tooltipText = "calculated in radians",
                value = gsb.areaAngle,
                isEnabled = mutableStateOf(false)
                )
            Spacer( Modifier.height( 5.dp ) )
            LabeledField(
                label = "max AGS",
                tooltipText = "calculated in radians per second",
                value = gsb.maxAGS,
                isEnabled = mutableStateOf(false)
                )
        } }

        Spacer( Modifier.height( 30.dp ) )
        Row {
            MyButton(
                buttonText = "load",
                buttonPos = ButtonPosition.Left
                )
            Spacer( Modifier.width( 3.dp ) )
            MyButton(
                buttonText = "save",
                buttonPos = ButtonPosition.Right
                )
            Spacer( Modifier.weight(1F) )
            MyButton(
                buttonText = "accept",
                buttonPos = ButtonPosition.Lonely,
                isEnabled = gsb.isCorrect,
                onClick = { gearSystem.value = gsb.build() }
                )
        }

    }
}

@Composable
fun TwinFormDisplay()
{

}

@ExperimentalFoundationApi
fun main() = application {
    Window (
        onCloseRequest = ::exitApplication,
        title = "???",
        state = WindowState(
            position = WindowPosition( Alignment.Center ),
            size = DpSize.Unspecified
            )
        )
    {
        val gearSystem : MutableState<GearSystem> = mutableStateOf(GearSystem())
        TwinForm( gearSystem )
    }
}