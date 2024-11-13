package com.example.contactformapp.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import coil.size.Size

import com.example.contactformapp.createImageFile


@Composable
fun CameraScreen(modifier : Modifier){

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "com.example.contactformapp",
        file
    )
    var captureImageUri by remember{
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture())
        { success ->
        if(success)
            captureImageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Kindly grant permission by going to settings", Toast.LENGTH_SHORT).show()
        }
    }

    Column(){

        Button(
            onClick = {
                val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if(permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                    cameraLauncher.launch(uri)
                }
                else{
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
            Text(text = "Upload Your Image")
        }
    }

    if (captureImageUri.path?.isNotEmpty() == true){
        Image(
            modifier = modifier.padding(16.dp, 8.dp),
            painter = rememberImagePainter(
                data = captureImageUri,
                builder = {
                    size(Size.ORIGINAL)
                }
            ),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview(){
    CameraScreen(modifier = Modifier)
}
