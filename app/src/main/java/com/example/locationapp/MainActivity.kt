package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.locationapp.ui.LocationUtils
import com.example.locationapp.ui.theme.LocationAppTheme
import com.example.locationapp.viewmodel.LocationViewmodel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val locationViewmodel:LocationViewmodel = viewModel()
            LocationAppTheme {

                val context= LocalContext.current
                LocationDisplay(LocationUtils(context),context)

            }
        }
    }
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils, context: Context
) {
    val requestPermissionLaucher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[Manifest.permission.ACCESS_FINE_LOCATION] == true && permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {

            } else {
                //Ask for location
                //IT STORE THE BOOLEANS WHETHTER IT SHOULD THE LOCATION PERMISSION REQUESRT
                val rationaleRequire = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (rationaleRequire) {
                    Toast.makeText(
                        context,
                        "Loacation permission is required for htis feature to work",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Go to the settings for the permission ", Toast.LENGTH_LONG
                    ).show()
                }

            }
        })

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Location is not available")

    Button(onClick = {
        if (locationUtils.hasLocationPermisssion(context)) {
            //permission already granted
        } else {
            //request for the permission
            requestPermissionLaucher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

        }
    }) {
        Text("Get Location")
    }

}}