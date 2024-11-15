package com.example.contactformapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.contactformapp.UserData


@Composable
fun ResultScreen(){

    val gender = UserData.gender
    val age = UserData.age
    val image = UserData.image
    val location = UserData.location
    val audio = UserData.audioFilePath

    val jsonData1 = remember() {
        """
        {
            "gender": "$gender",
            "age": "$age",
            "image": "$image",
            
        }
        """.trimIndent()
    }
    val jsonData2 = remember() {
        """
        {
            "audio": "$audio",
            "location": "$location"
        }
        """.trimIndent()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "RESULT")

        Text(text = jsonData1)

        UserData.image?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Uploaded Image",
                modifier = Modifier.size(100.dp)
            )
        }

        Text(text = jsonData2)
    }

}