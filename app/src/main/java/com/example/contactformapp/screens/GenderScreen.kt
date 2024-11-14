package com.example.contactformapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@SuppressLint("RememberReturnType", "UseOfNonLambdaOffsetOverload")
@Composable
fun GenderScreen(modifier: Modifier,
                 navController: NavController){



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
    val icon = if(expanded){
        Icons.Filled.KeyboardArrowUp
    }
    else{
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "Q1. Select your gender",
            modifier = modifier.padding(6.dp)
        )

        OutlinedTextField(value = selectedOption,
            onValueChange = {
                //selectedOption = it
            },
            modifier = modifier
                //to assign same width to the drop down menu as the text field
                .onGloballyPositioned { coordinates ->
                size = coordinates.size //captures size of the text field
            },
            label = { Text(text = "Gender")},
            trailingIcon = {
                Icon(
                    icon, contentDescription = "Dropdown Arrow",
                    modifier = Modifier.clickable { expanded = !expanded })
            }
        )

        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier = Modifier
                .width(with(LocalDensity.current) { size.width.toDp() })
                .offset(y = with(LocalDensity.current) { size.height.toDp() }) //position dropdown below the text field
        ) {
            options.forEach{lable->
                DropdownMenuItem(
                    text = { "Gender" },
                    onClick = {
                        selectedOption = lable
                        expanded = false
                    }
                )
            }
        }

        Button(onClick = {
            navController.navigate("AgeScreen")
        }) {
            Text(text = "Next")
        }



    }
}


