package com.example.contactformapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun PermissionRequest(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
){
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted){
            onPermissionGranted()
        }
        else{
            onPermissionDenied()
        }

    }

    LaunchedEffect(Unit) {
        when(PackageManager.PERMISSION_GRANTED){
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)->{
                onPermissionGranted()
            }
            else ->{
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

}