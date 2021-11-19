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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s452635.detector.detecting.ScanQueue

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

// TODO : switch to LabelField2
@ExperimentalFoundationApi
@Composable
fun LabeledField(
    label: String,
    value: MutableState<String>,
    onValueChange: () -> Unit = {},
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
            onValueChange = { value.value = it; onValueChange() },
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
@ExperimentalFoundationApi
@Composable
fun LabeledField2(
    label: String,
    value: MutableState<String>,
    onValueChange: () -> Unit = {},
    isEnabled: Boolean = true,
    isCorrect: Boolean = true,
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
        if( !isEnabled ) return MyColors.DisabledMain
        if( !isCorrect ) return MyColors.IncorrectRed
        return MyColors.Primary
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height( 30.dp )
            .width( width )
            .background(
                color = if( isEnabled ) Color.White else MyColors.DisabledBack,
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
            onValueChange = { value.value = it; onValueChange() },
            readOnly = !isEnabled,
            maxLines = 1,
            textStyle = TextStyle(
                color = if(isEnabled) Color.Black else MyColors.DisabledMain,
                fontStyle = if(isEnabled) FontStyle.Normal else FontStyle.Italic
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

@Composable
fun PatheticBorder(
    labelText: String = "",
    content : @Composable() (() -> Unit)
) {
    Column {
        if( labelText != "" )
        {
            Text(
                text = labelText,
                color = MyColors.DisabledBack,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding( 10.dp, 0.dp )
                )
        }
        Column(
            modifier = Modifier
                .height( IntrinsicSize.Max )
                .border( 1.dp, MyColors.DisabledBack )
                .padding( 10.dp ),
        ) {
            content()
        }
    }
}

@Composable
fun ScanRow(
    scanQueue : MutableState<ScanQueue>
) {
    val size : Dp = 24.dp
    @Composable
    fun TheList( list : List<String> )
    {
        Row( modifier = Modifier.border( 2.dp, Color.Black, MyShapes.Even ) )
        {
            list.forEach {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width( size )
                    .border( 1.dp, Color.Black, MyShapes.Even )
                    .background( Color.White )
                    .padding( 5.dp )
                )
            }
        }
    }
    @Composable
    fun TheMainItem( text : String )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding( 2.dp )
                    .height( 48.dp )
                    .border( 3.dp, MyColors.Primary, MyShapes.Uneven )
                    .padding( 5.dp )
        )
        {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width( size )
                    .border( 2.dp, Color.Black, MyShapes.Even )
                    .background( Color.White )
                    .padding( 5.dp )
                )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "ScA",
            color = MyColors.Primary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp ,
            modifier = Modifier.padding( 0.dp, 0.dp, 10.dp, 0.dp )
            )
        TheList( scanQueue.value.preQueue.value )
        TheMainItem( scanQueue.value.currentItem.value!! )
        TheList( scanQueue.value.postQueue.value.take( scanQueue.value.postQueueSize ) )
    }
}

class NumberFieldManager (
    val label : String = "no name",
    val tooltip : String = "no tooltip",
    val correctionChecking : ( NumberFieldManager ) -> Boolean
) {
    val field : MutableState<String> = mutableStateOf("")
    var number : Int? = null
    var isCorrect : Boolean = false

    fun onValueChange()
    {
        fun isFieldADigit() : Boolean
        {
            field ?: return false
            if( !field.value.matches( Regex("\\d++") ) ) { return false }

            return true
        }

        number =
            if( isFieldADigit() ) field.value.toInt()
            else null

        isCorrect =
            if( number == null ) false
            else correctionChecking( this )
    }
}

@Composable
fun dippingField (
    sample : AnnotatedString,
    width : Dp = 300.dp
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width( width )
            .background( MyColors.DisabledBack, MyShapes.Uneven )
            .padding( 10.dp )
    ) {
        Text (
            text = sample,
        )
    }
}
