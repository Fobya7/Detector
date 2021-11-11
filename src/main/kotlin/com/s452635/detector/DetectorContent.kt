package com.s452635.detector

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun DetectorContent(openDialog: () -> Unit )
{
    var selectedOption by remember { mutableStateOf(StartOption.ReadFromFile) }

    MaterialTheme { Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {

        Box(
            modifier = Modifier
                .border( 2.dp, colorBorderGray, shape = shapeRounded )
        ) {
        Row(
            modifier = Modifier
                .height( IntrinsicSize.Max )
                .padding( 10.dp )
        )
        {

            Button(
                onClick = { openDialog() },
                content = { Text( "start" ) },
                colors = ButtonDefaults.buttonColors( backgroundColor = colorPrimary ),
                modifier = Modifier
                    .fillMaxHeight()
                    .background( shape = shapeRounded, color = colorPrimary )
                )

            Column(
                horizontalAlignment = Alignment.Start
            ) {
            StartOption.values().forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding( 2.dp )
                    .height( IntrinsicSize.Max )
                )
            {
                RadioButton(
                    selected = it == selectedOption,
                    colors = RadioButtonDefaults.colors( selectedColor = colorPrimary ),
                    interactionSource = MutableInteractionSource(),
                    onClick = { selectedOption = it },
                    modifier = Modifier.height( 20.dp )
                    )
                Text( text = it.optionString )
            } } }

        } }

    } }
}

enum class StartOption( val optionString: String )
{
    LiveGeneration( "use live generator" ),
    ReadFromFile( "read from file" )
}

val colorBorderGray = Color( 95, 95, 95 )
val colorPrimary = Color( 252, 152, 3 )
val shapeRounded = RoundedCornerShape( 5.dp )