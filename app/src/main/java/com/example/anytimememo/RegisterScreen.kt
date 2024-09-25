package com.example.anytimememo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun RegistrationScreen(
    viewModel: AuthViewModel,
    navController: NavHostController
){
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var confirmPassword by remember {mutableStateOf("")}
    var message by remember {mutableStateOf("")}

    //val authState = viewModel.authState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text ("Email")},
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text ("Password")},
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = { Text ("Confirm Password")},
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                //a button but usage of going back and register
                if(email == "" || password == "" || confirmPassword == ""){
                    navController.popBackStack("Logging", false)
                } else if(password == confirmPassword){
                    viewModel.register(email, password) { result ->
                        message = result
                    }
                } else {
                    message = "Password do not match"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0))
        ){
            if(email == "" || password == "" || confirmPassword == ""){
                Text("Go Back", color = Color.Black)
            } else{
                Text("Register", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                navController.navigate("LogIN")

        }){
            Text("Already have an account? Log in here")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)
        //when(authState.value)
        if(message == "Registration successful"){
            // Use LaunchedEffect to delay navigation
            LaunchedEffect(Unit) {
                delay(2500) // Wait for 2.5 seconds
                //nav back to specific page
                navController.popBackStack("Logging", false) // Navigate back to Login") // Navigate back
            }
        }

    }
}


