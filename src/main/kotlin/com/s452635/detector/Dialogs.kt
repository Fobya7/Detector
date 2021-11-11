package com.s452635.detector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.io.File
import javax.swing.JFileChooser

val chooser = JFileChooser()
fun filePickingDialog() : File?
{
    chooser.showOpenDialog( null )
    return chooser.selectedFile
}

@Composable
fun formTest()
{
    var textInput by remember { mutableStateOf("") }

    MaterialTheme { Column(
        modifier = Modifier
            .wrapContentHeight()
            .width( IntrinsicSize.Min )
            .padding( 15.dp ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {

        listOf( "hewwo", "form" ).forEach {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height( IntrinsicSize.Max )
                .width( 300.dp )
                .background( colorPrimary, shapeRounded )
                .border( 2.dp, colorPrimary, shapeRounded )
            )
        {
            Text(
                text = it,
                color = Color.White,
                modifier = Modifier
                    .padding( 10.dp, 5.dp )
                    .width( 100.dp )
            )
            Box(
                Modifier
                    .background( Color.White, shapeRounded )
            )
            {
            BasicTextField (
                value = textInput,
                onValueChange = { textInput = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding( 7.dp )
                )
            }
        }
        Spacer( Modifier.height( 10.dp ) )
        }

        Spacer( Modifier.height( 15.dp ) )

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            )
        {
            Button (
                onClick = {},
                content = { Text( "load" ) },
                colors = ButtonDefaults.buttonColors( backgroundColor = colorPrimary ),
                shape = shapeRounded,
                modifier = Modifier
                    .wrapContentWidth()
                    .height( 40.dp )
                    .weight( 3F )
                    .fillMaxWidth()
                )
            Spacer( Modifier.width( 5.dp ) )
            Button (
                onClick = {},
                content = { Text( "save" ) },
                colors = ButtonDefaults.buttonColors( backgroundColor = colorPrimary ),
                shape = shapeRounded,
                modifier = Modifier
                    .wrapContentWidth()
                    .height( 40.dp )
                    .weight( 3F )
                    .fillMaxWidth()
                )
            Spacer( Modifier.width( 15.dp ) )
            Button (
                onClick = {},
                content = { Text( "accept" ) },
                colors = ButtonDefaults.buttonColors( backgroundColor = colorPrimary ),
                shape = shapeRounded,
                modifier = Modifier
                    .wrapContentWidth()
                    .height( 40.dp )
                    .weight( 5F )
                    .fillMaxWidth()
                )
        }
    } }
}

// TODO : testing main
fun main() = application {
    Window (
        onCloseRequest = ::exitApplication,
        title = "Gear System",
        state = WindowState(
            position = WindowPosition( Alignment.Center ),
            size = DpSize.Unspecified
            )
        )
    {
        formTest()
    }
}