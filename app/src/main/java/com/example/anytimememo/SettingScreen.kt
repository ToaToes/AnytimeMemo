package com.example.anytimememo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anytimememo.ui.note.PrivateMemoTaking
import com.example.anytimememo.ui.note.SaveNoteToDatabase
import com.example.anytimememo.ui.theme.AnytimeMemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White)
            ){
                LocationServicesToggleSwitch()
            }
        }
    )

}

@Composable
fun LocationServicesToggleSwitch(){

    var isChecked by remember {
        mutableStateOf(true)
    }

    //open location setting
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

    val context = LocalContext.current
    /*
    //val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var locationServicesEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        locationServicesEnabled = ContextCompat.checkSelfPermission(
            context, "Manifest.permission.ACCESS_FINE_LOCATION"
        ) == PackageManager.PERMISSION_GRANTED
    }*/

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ){
        Text(
            text = if(isChecked) "Location Service Enabled" else "Location Service Disabled",
            modifier = Modifier.padding(top = 12.dp, start = 20.dp)
            )
        Switch(
            checked = isChecked,
            onCheckedChange = { checkedState ->
                Toast.makeText(context, "Opening Location Settings ... ", Toast.LENGTH_SHORT).show()
                context.startActivity(intent)
                isChecked = checkedState
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 80.dp)
        )
    }
}

@Preview
@Composable
fun SettingPagePreview(){
    AnytimeMemoTheme {
        SettingPage()
    }
}