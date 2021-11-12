package com.s452635.detector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainColumn( content: @Composable() (ColumnScope.() -> Unit) )
{
    MaterialTheme {
        Column(
        modifier = Modifier
            .padding( 20.dp )
            .wrapContentSize( unbounded = true ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
        )
    }
}

@Composable
fun MyText( label: String, value: String, changeValue: (String) -> Unit )
{
    Row (
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .height( IntrinsicSize.Max )
        .width( 300.dp )
        .background( MyColors.Primary, MyShapes.ShapeRounded )
        )
    {
        Text (
            text = label,
            color = Color.White,
            modifier = Modifier
                .padding( 10.dp, 5.dp )
                .width( 100.dp )
            )
        Box (
            modifier = Modifier
                .background( Color.White, MyShapes.ShapeRounded )
                .padding( 3.dp )
                .weight( 1F )
            )
        {
            BasicTextField (
                value = value,
                onValueChange = changeValue,
                singleLine = true,
                modifier = Modifier
                    .wrapContentHeight()
                )
        }
        Spacer( Modifier.width( 13.dp ) )
    }
}
