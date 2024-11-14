package com.example.contactformapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactformapp.audiorecorder.location.LocationUtils
import com.example.contactformapp.audiorecorder.location.LocationViewModel
import com.example.contactformapp.screens.AgeScreen
import com.example.contactformapp.screens.CameraScreen
import com.example.contactformapp.screens.GenderScreen
import com.example.contactformapp.screens.SubmitScreen



@Composable
fun Navigation(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "GenderScreen"){
        composable("GenderScreen"){
            GenderScreen(modifier = Modifier, navController = navController)
        }
        composable("AgeScreen"){
            AgeScreen(modifier = Modifier, navController = navController)
        }
        composable("CameraScreen"){
            CameraScreen(modifier = Modifier, navController = navController)
        }
        composable("SubmitScreen"){
            SubmitScreen(Modifier, locationUtils, viewModel )
        }
    }
}