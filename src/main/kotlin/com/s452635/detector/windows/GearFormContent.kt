package com.s452635.detector.windows

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GearSystemBuilder
import com.s452635.detector.styling.*

class GearFormState(
    val isVisible : MutableState<Boolean>,
    val onClose : () -> Unit,
    val onClickAccept : () -> Unit,
    val gearSystem : MutableState<GearSystem>
)

@ExperimentalFoundationApi
@Composable
fun GearFormWindow(
    gearFormState : GearFormState
) = Window(
    onCloseRequest = gearFormState.onClose,
    visible = gearFormState.isVisible.value,
    title = "Gear Form",
    state = rememberWindowState(
        position = WindowPosition( 560.dp, 200.dp ),
        width = Dp.Unspecified, height = Dp.Unspecified
        )
    )
{
    GearFormContent(
        onClickAccept = gearFormState.onClickAccept,
        gearSystem = gearFormState.gearSystem
        )
}

@ExperimentalFoundationApi
@Composable
fun GearFormContent(
    onClickAccept : () -> Unit,
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
                onClick = { gearSystem.value = gsb.build(); onClickAccept() }
                )
        }

    }
}