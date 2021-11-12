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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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

// "Gear System"
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
                .background( MyColors.Primary, MyShapes.ShapeRounded )
                .border( 2.dp, MyColors.Primary, MyShapes.ShapeRounded )
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
                    .background( Color.White, MyShapes.ShapeRounded )
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
                colors = ButtonDefaults.buttonColors( backgroundColor = MyColors.Primary ),
                shape = MyShapes.ShapeRounded,
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
                colors = ButtonDefaults.buttonColors( backgroundColor = MyColors.Primary ),
                shape = MyShapes.ShapeRounded,
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
                colors = ButtonDefaults.buttonColors( backgroundColor = MyColors.Primary ),
                shape = MyShapes.ShapeRounded,
                modifier = Modifier
                    .wrapContentWidth()
                    .height( 40.dp )
                    .weight( 5F )
                    .fillMaxWidth()
                )
        }
    } }
}

@Composable
fun main2()
{
    MaterialTheme { Column(
        modifier = Modifier
            .wrapContentSize()
            .padding( 15.dp ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {

        listOf( "HL Input", "Gear System" ).forEach {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height( IntrinsicSize.Max )
        )
        {
            Text(
                text = it,
                modifier = Modifier
                    .width( 120.dp )
                    .padding( 5.dp, 0.dp )
                )
            BasicTextField(
                value ="aaaaaaaaaaa",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .background( Color.White )
                    .padding( 0.dp, 3.dp )
                    .width( 200.dp )
                )
            Spacer( Modifier.width( 5.dp ) )
            listOf( "EDIT", "LOOK" ).forEach { innerIt ->
                Button(
                    content = { Text(innerIt) },
                    contentPadding = PaddingValues( 0.dp ),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors( backgroundColor = MyColors.Primary ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .width( 60.dp )
                        .height( 30.dp )
                    )
            }
        }
        Spacer( Modifier.height( 5.dp ) )
        }

    } }
}

@Composable
fun mainScreen()
{
    MaterialTheme { Column(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding( 15.dp ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        var textInput by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background( Color.LightGray )
                .wrapContentWidth()
                .height( IntrinsicSize.Max )
                .padding( 3.dp )
        )
        {
            Text(
                text = "HL INPUT:",
                // Modifier.background( Color.Red )
                )
            Spacer( Modifier.width( 2.dp ) )
            BasicTextField (
                value = "textInput",
                onValueChange = { textInput = it },
                textStyle = TextStyle( fontStyle = FontStyle.Italic ),
                singleLine = true,
                readOnly = true,
                modifier = Modifier
                    .width( 150.dp )
                    .wrapContentHeight()
                    .padding( 7.dp )
                )
            listOf( "EDIT", "LOOK" ).forEach {
                Spacer( Modifier.width( 3.dp ) )
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    content = { Text( it ) },
                    colors = ButtonDefaults.buttonColors( backgroundColor = MyColors.Primary ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .width( 60.dp )
                        .height( 30.dp )
                    )
            }
        }

    } }
}

@Composable
fun mainText2()
{
    var textInput by remember { mutableStateOf("") }

    MainColumn {
        MyText( "hewwo", textInput ) { textInput = it }
    }
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
        mainText2()
    }
}