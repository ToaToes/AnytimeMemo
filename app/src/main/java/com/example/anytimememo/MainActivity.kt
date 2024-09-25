package com.example.anytimememo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.anytimememo.ui.theme.AnytimeMemoTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

//implementation(libs.hilt.navigation.compose) !!!
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    //database will be later initialise
    private lateinit var database: DatabaseReference

    //location permission
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var  fusedLocationClient: FusedLocationProviderClient
    private val cityViewModel: CityViewModel by lazy { ViewModelProvider(this)[CityViewModel::class.java] }

    //registration firebase
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        //permission Launcher
        //https://stackoverflow.com/questions/29441384/fusedlocationapi-getlastlocation-always-null
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        database = Firebase.database.reference

        setContent {
            LocationScreen()
            AnytimeMemoTheme {
                AppBarList(authViewModel, database)
            }
        }
    }

    @Composable
    fun LocationScreen() {
        val context = LocalContext.current
        //longitude and ...
        //var location by remember { mutableStateOf<String?>(null) }
        //city name
        var cityName by remember { mutableStateOf<String?>(null) }
        var hasFineLocationPermission by remember { mutableStateOf(false) }
        //var hasCoarseLocationPermission by remember { mutableStateOf(false) }
        var showEnableLocationDialog by remember { mutableStateOf(false) }

        // Request location permissions
        permissionsLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            hasFineLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            //hasCoarseLocationPermission = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (hasFineLocationPermission) {
                fetchLocation(context) { loc ->
                    //location = loc
                    cityName = loc
                    cityViewModel.updateCityName(loc)
                }
            } else {
                showEnableLocationDialog = true
                cityViewModel.updateCityName("Permission Denied")
            }
        }
        // Request permissions if not already granted
        LaunchedEffect(Unit) {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        if (showEnableLocationDialog) {
            AlertDialog(
                onDismissRequest = { showEnableLocationDialog = false },
                title = { Text("Location Services Required") },
                text = { Text("Please enable location services to proceed.") },
                confirmButton = {
                    Button(onClick = {
                        showEnableLocationDialog = false
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        context.startActivity(intent)
                    }) {
                        Text("Open Settings")
                    }
                },
                dismissButton = {
                    Button(onClick = { showEnableLocationDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    //display city name
    private fun fetchLocation(context: Context, onLocationFetched: (String) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { task: Location? ->
                Log.d("Location", "Location: $task")
                if (task != null) {
                    //val location = task.result
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(task.latitude, task.longitude, 1)
                    if (addresses != null) {
                        if (addresses.isNotEmpty()) {
                            val cityName = addresses[0]?.locality ?: "Unknown City"
                            onLocationFetched(cityName)
                        } else {
                            onLocationFetched("City not found")
                        }
                    }
                } else {
                    onLocationFetched("Location not available")
                }
            }
        } else {
            onLocationFetched("Permission not granted")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnytimeMemoTheme {
        val authViewModel = AuthViewModel()
        val database = Firebase.database.reference
        AppBarList(authViewModel, database)
    }
}