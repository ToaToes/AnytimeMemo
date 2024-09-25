package com.example.anytimememo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    navController: NavController
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = viewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> {
                navController.popBackStack("Home", false)
                Toast.makeText(
                    context,
                    "You are already Logged in",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //Text(text = "Log In"), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Login Email")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Login Password")
            },
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(email == "" || password == ""){
                    navController.popBackStack("Logging", false)
                }
                else {
                    viewModel.login(email, password)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0))
        ){

            if(email == "" || password == ""){
                Text("Go Back", color = Color.Black)
            } else{
                Text("Log In", color = Color.Black)
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(
            onClick = {
                navController.navigate("Register")
            }
        ) {
            Text(text = "Don't have an account? Create one here")
        }
    }

}