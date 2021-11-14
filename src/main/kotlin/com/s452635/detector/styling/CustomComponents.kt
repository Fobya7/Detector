package com.s452635.detector.styling

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainColumn( content: @Composable() (ColumnScope.() -> Unit) )
{
    MaterialTheme {
        Column(
        modifier = Modifier
            .padding( 20.dp )
            .wrapContentHeight( unbounded = true )
            .width( IntrinsicSize.Max ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun LabeledButton(
    buttonText : String,
    label : MutableState<String> = mutableStateOf( "" ),
    onClick : () -> Unit = {},
    isEnabled : MutableState<Boolean> = mutableStateOf( true ),
    width: Dp = 270.dp,
    buttonFraction: Float = 0.42F
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MyColors.DisabledBack, MyShapes.UnevenLeft)
            .height( 30.dp )
            .width( width )
    )
    {
        Button(
            content = { Text( buttonText, color = Color.White ) },
            onClick = onClick,
            enabled = isEnabled.value,
            contentPadding = PaddingValues( 10.dp, 0.dp ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MyColors.Primary,
                disabledBackgroundColor = MyColors.DisabledMain
                ),
            shape = MyShapes.Uneven,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth( buttonFraction )
            )
        BasicTextField(
            value = label.value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                color = MyColors.DisabledMain,
                fontStyle = FontStyle.Italic
                ),
            modifier = Modifier
                .padding( 10.dp, 0.dp )
            )
    }
}

@ExperimentalFoundationApi
@Composable
fun LabeledField(
    label: String,
    value: MutableState<String>,
    isEnabled: MutableState<Boolean> = mutableStateOf( true ),
    correctionChecking: () -> Boolean = { true },
    width: Dp = 270.dp,
    labelFraction: Float = 0.42F,
    tooltipText : String? = null
) {

    @Composable
    fun tooltipInsides()
    {
        if( tooltipText == null ) { return }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background( MyColors.GhostlyBackground )
                .padding( 10.dp, 5.dp )
            )
        {
            Text(
                text = tooltipText,
                fontSize = 12.sp,
                color = MyColors.GhostlyGray,
                fontStyle = FontStyle.Italic
                )
        }

    }

    fun mostColor() : Color
    {
        if( !isEnabled.value ) return MyColors.DisabledMain
        if( correctionChecking.invoke() ) return MyColors.Primary
        return MyColors.IncorrectRed
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height( 30.dp )
            .width( width )
            .background(
                color = if( isEnabled.value ) Color.White else MyColors.DisabledBack,
                shape = MyShapes.Uneven
                )
            .border(
                width =  2.dp,
                color = mostColor(),
                shape = MyShapes.Uneven
                )
    )
    {
        TooltipArea(
            tooltip = { tooltipInsides() },
            tooltipPlacement = TooltipPlacement.CursorPoint(
                offset = DpOffset(10.dp, 0.dp)
                )
            )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = mostColor(),
                        shape = MyShapes.UnevenLeft
                        )
                    .fillMaxHeight()
                    .fillMaxWidth( labelFraction )
                )
            {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding( 10.dp, 0.dp )
                    )
            }
        }
        BasicTextField(
            value = value.value,
            readOnly = !isEnabled.value,
            onValueChange = { value.value = it },
            maxLines = 1,
            textStyle = TextStyle(
                color = if(isEnabled.value) Color.Black else MyColors.DisabledMain,
                fontStyle = if(isEnabled.value) FontStyle.Normal else FontStyle.Italic
            ),
            modifier = Modifier
                .padding(10.dp, 0.dp)
        )

    }
}

@Composable
fun MyButton(
    buttonText : String = "",
    onClick : () -> Unit = {},
    isEnabled : MutableState<Boolean> = mutableStateOf(true),
    buttonPos : ButtonPosition = ButtonPosition.Lonely,
    buttonSize : ButtonSize = ButtonSize.Tiny
) {
    Button(
        content = { Text( buttonText, color = Color.White ) },
        contentPadding = PaddingValues( 20.dp, 0.dp ),
        onClick = onClick,
        enabled = isEnabled.value,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MyColors.Primary,
            disabledBackgroundColor = MyColors.DisabledMain
            ),
        shape = when( buttonPos ) {
            ButtonPosition.Lonely -> MyShapes.Uneven
            ButtonPosition.Left -> MyShapes.UnevenLeft
            ButtonPosition.Center -> MyShapes.Even
            ButtonPosition.Right -> MyShapes.UnevenRight
            },
        modifier = Modifier
            .height( when( buttonSize ) {
                ButtonSize.Tiny -> 30.dp
                ButtonSize.Biggie -> 50.dp
                })
        )
}
enum class ButtonPosition
{
    Lonely, Center, Right, Left
}
enum class ButtonSize
{
    Biggie, Tiny
}