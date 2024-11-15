package com.example.contactformapp.audiorecorder.location

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import com.example.contactformapp.MainActivity

@Composable
fun LocationDisplay(
    context: Context,
    locationUtils: LocationUtils,
    viewModel: LocationViewModel
){
    val location = viewModel.location

    var address = remember{
        mutableStateOf("Fetching address....")
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions->
            if(permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
                &&
                permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true)
            {
                //I have access to location

                locationUtils.requestLocationUpdates(viewModel = viewModel)
            }
            else run {
                //Ask for permission
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if(rationaleRequired){
                    Toast.makeText(context,
                        "Location permission is required for this feature to work",
                        Toast.LENGTH_LONG)
                        .show()
                }
                else{
                    Toast.makeText(context,
                        "Location permission is required. Please enable it in android settings.",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    )

    if(locationUtils.hasLocationPermission(context))
    {
        //Permission already granted update location
        locationUtils.requestLocationUpdates(viewModel)

        location?.let {
            locationUtils.reverseGeocodeLocation(it) { geoAddress ->
                address.value = geoAddress
            }

            Text("Address: ${location.latitude}, ${location.longitude} \n$address")
        } ?: run {
            Text("Fetching location...")
        }
    } else {
        // Request permission
        SideEffect {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}


