package com.example.contactformapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.contactformapp.UserData

@Composable
fun AgeScreen(modifier : Modifier,
              navController : NavController){



    var age by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var isError by remember { mutableStateOf(false) }
    val errorMessage = when {
        age.isEmpty() -> "Age is required."
        age.toIntOrNull() == null -> "Enter a valid number."
        age.toInt() !in 1..120 -> "Enter an age between 1 and 120."
        else -> null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Q2. Enter your age")

        OutlinedTextField(value = age ,
            onValueChange = {
                age = it
                isError = errorMessage != null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Age")},
            isError = isError
        )

        if (isError) {
            Text(
                text = errorMessage ?: "",
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Button(onClick = {
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            else{
                UserData.age = age
                //navigate
                navController.navigate("CameraScreen")
            }
        }) {
            Text(text = "Next")
        }
    }
}
