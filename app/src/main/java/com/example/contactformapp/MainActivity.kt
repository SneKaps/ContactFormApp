package com.example.contactformapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.contactformapp.audiorecorder.location.LocationUtils
import com.example.contactformapp.audiorecorder.location.LocationViewModel
import com.example.contactformapp.screens.CameraScreen
import com.example.contactformapp.screens.GenderScreen
import com.example.contactformapp.ui.theme.ContactFormAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val locationUtils = LocationUtils(this)

            val viewModel: LocationViewModel = viewModel()

            ContactFormAppTheme {
                Navigation(locationUtils, viewModel)
                //CameraScreen(modifier = Modifier)
                //GenderScreen(modifier = Modifier)
            }
        }
    }
}

