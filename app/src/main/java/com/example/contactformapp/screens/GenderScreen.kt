package com.example.contactformapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.contactformapp.PermissionRequest
import com.example.contactformapp.UserData
import com.example.contactformapp.audiorecorder.playback.AndroidAudioPlayer
import com.example.contactformapp.audiorecorder.recorder.AndroidAudioRecorder
import java.io.File


@SuppressLint("RememberReturnType", "UseOfNonLambdaOffsetOverload")
@Composable
fun GenderScreen(modifier: Modifier,
                 navController: NavController){
    val context = LocalContext.current
    val cacheDir = context.cacheDir

    var audioFile : File? = null

    var recorder by remember { mutableStateOf<AndroidAudioRecorder?>(null) }

    //variable to hold state of expanded menu
    var expanded by remember{
        mutableStateOf(false)
    }

    //list of options in dropdown menu
    val options = listOf("Male", "Female", "Other")

    //variable to store selected option
    var selectedOption by remember{
        mutableStateOf("")
    }

    //to ensure that size of drop down menu is same as the text field
    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    //adjusting icon as per the drop down menu
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PermissionRequest(
                onPermissionGranted = {
                    audioFile = File(cacheDir, "audio.mp3")
                    recorder = AndroidAudioRecorder(context = context, filePath = audioFile!!.absolutePath)
                    recorder?.start(audioFile!!)
                    },
                onPermissionDenied = {
                    Toast.makeText(
                        context,
                        "Microphone permission is required to record audio.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Q1. Select your gender",
                modifier = modifier.padding(6.dp)
            )

            Box(
                modifier = modifier
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                OutlinedTextField(value = selectedOption,
                    onValueChange = {

                    },
                    modifier = modifier
                        //to assign same width to the drop down menu as the text field
                        .onGloballyPositioned { coordinates ->
                            size = coordinates.size //captures size of the text field
                        },
                    label = { Text(text = "Gender") },
                    trailingIcon = {
                        Icon(
                            icon, contentDescription = "Dropdown Arrow",
                            modifier = Modifier.clickable { expanded = !expanded })
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    options.forEach { label ->
                        DropdownMenuItem(
                            text = { Text(text = label) },
                            onClick = {
                                selectedOption = label
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(onClick = {
                if(selectedOption.isNotEmpty()) {
                        UserData.gender = selectedOption
                        navController.navigate("AgeScreen")
                }

            }) {
                Text(text = "Next")
            }
        }
    }
}


