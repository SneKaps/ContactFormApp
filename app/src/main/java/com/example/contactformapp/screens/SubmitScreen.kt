package com.example.contactformapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

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
import androidx.navigation.NavController
import com.example.contactformapp.UserData
import com.example.contactformapp.audiorecorder.location.LocationDisplay
import com.example.contactformapp.audiorecorder.location.LocationUtils
import com.example.contactformapp.audiorecorder.location.LocationViewModel
import com.example.contactformapp.audiorecorder.recorder.AndroidAudioRecorder
import java.io.File

@SuppressLint("SuspiciousIndentation")
@Composable
fun SubmitScreen(modifier: Modifier,
                 locationUtils: LocationUtils,
                 viewModel: LocationViewModel,
                 navController: NavController){

    val context = LocalContext.current

    val cacheDir = context.cacheDir

    val audioFile = remember { File(cacheDir, "audio.mp3") }

    val recorder by remember {
        mutableStateOf(AndroidAudioRecorder(context, audioFile.absolutePath))
    }

    var isRecording by remember { mutableStateOf(true) }
    var locationFetched by remember { mutableStateOf(false) }
    //var showLocationDisplay by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            if(isRecording){
                recorder.stop()
                UserData.audioFilePath = recorder.filePath
            }
            isRecording = false

            if (locationUtils.hasLocationPermission(context)) {
                locationUtils.getLastKnownLocation(
                    onSuccess = { location ->
                        UserData.location = "${location.latitude}, ${location.longitude}" // Save raw location
                        locationUtils.reverseGeocodeLocation(location) {
                            //UserData.location = address // Save human-readable address
                            locationFetched = true
                            navController.navigate("ResultScreen"){
                                popUpTo("SubmitScreen") {inclusive = true}
                            }
                        }
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to fetch location.", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(context, "Location permission not granted.", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (locationFetched) {
            Text(text = "Location fetched successfully!")
        }

        Spacer(modifier = Modifier.height(10.dp))

        LocationDisplay(context = context, locationUtils = locationUtils, viewModel = viewModel)

    }
}


