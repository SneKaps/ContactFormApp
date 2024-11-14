package com.example.contactformapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.contactformapp.PermissionRequest
import com.example.contactformapp.audiorecorder.location.LocationDisplay
import com.example.contactformapp.audiorecorder.location.LocationUtils
import com.example.contactformapp.audiorecorder.location.LocationViewModel
import com.example.contactformapp.audiorecorder.recorder.AndroidAudioRecorder
import java.io.File

@Composable
fun SubmitScreen(modifier: Modifier,
                 locationUtils: LocationUtils,
                 viewModel: LocationViewModel){

    val context = LocalContext.current

    val recorder by lazy {
        AndroidAudioRecorder(context)
    }

    var isRecording by remember { mutableStateOf(true) }
    var showLocationDisplay by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            if(isRecording)
            recorder.stop()
            isRecording = false
            showLocationDisplay = true
        }) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (showLocationDisplay){
            LocationDisplay(context = context, locationUtils = locationUtils, viewModel = viewModel)
        }





    }
}