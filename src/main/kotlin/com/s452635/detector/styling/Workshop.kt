package com.s452635.detector.styling

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.GearSystemBuilder
import com.s452635.detector.detecting.ScanQueue
import java.lang.Thread.sleep
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
        LabeledField(
            label = "HL Input",
            value = rad2,
            isEnabled = mutableStateOf(false)
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

// TODO : checking if all input correct
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun formStyle(
    isEnabled : MutableState<Boolean> = mutableStateOf( true ),
    gearSystemBuilder : GearSystemBuilder = GearSystemBuilder()
) {

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
            LabeledField(
                label = "detector tick",
                value = mutableStateOf("")
                )
        } }

        Spacer( Modifier.height( 15.dp ) )
        PatheticBorder( "calculables" ) { Column {
            LabeledField(
                label = "max AGS",
                value = gearSystemBuilder.getMaxAGS(),
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
        Row()
        {
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
            sleep( 500 )
        }
    }

    MainColumn {
        ScanRow( mutableStateOf(one) )
    }
}

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
        formStyle()
    }
}