package com.example.anytimememo

//import com.example.cupcake.R
//import com.example.cupcake.data.DataSource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.anytimememo.ui.theme.AnytimeMemoTheme


@Composable
fun LoginPage(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        Row(
            modifier = Modifier
                //.fillMaxWidth()
                //.padding(1.dp),
        ){
            //AppBarList()

        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Main LOGO",
                modifier = Modifier.width(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))


        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            LoginButton(
                onClick = {
                    navController.navigate("LogIN")
                }
            )
            CreateAccountButton(
                onClick = {
                    navController.navigate("Register")
                        // This will pop itself in the route stack
                        /*navController.graph.startDestinationRoute?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true*/
                }
            )

        }
        Row(

        ){

        }
    }
}

@Composable
fun LoginButton(
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.widthIn(min = 250.dp)
    ){
        Text(text = "Log In",
            color = Color.Black,
            fontSize = 20.sp)

    }
}

@Composable
fun CreateAccountButton(
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.widthIn(min = 250.dp)
    ){
        Text(text = "Create Account",
            color = Color.Black,
            fontSize = 20.sp)

    }
}

@Preview
@Composable
fun LoginPagePreview(){
    val navController = NavController(LocalContext.current)
    AnytimeMemoTheme {
        LoginPage(navController)
    }
}