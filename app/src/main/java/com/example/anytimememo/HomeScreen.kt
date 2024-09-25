package com.example.anytimememo

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.example.cupcake.R
//import com.example.cupcake.data.DataSource
import com.example.anytimememo.ui.theme.AnytimeMemoTheme

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        //Arrangement.spaceBy(spaceBetween) gives only default size
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        Row(
            modifier = Modifier
            //.height()
            //.padding(1.dp),
        ){
            //AppBarList()
        }

        //Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Column(
            modifier = Modifier.fillMaxWidth(),//.weight(1f),
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
            StartButton(
                onClick = {
                    navController.navigate("Memo"){
                        // To get back
                        navController.graph.startDestinationRoute?.let { id ->
                            popUpTo(id) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            if(
                authViewModel.authState.value == AuthState.Authenticated
            ){
                //has to pass auth
                CreatePrivateMemoButton(
                    onClick = {
                        navController.navigate("PrivateMemo")
                    }
                )
            }
        }
        Row(

        ){

        }
    }
}

@Composable
fun CreatePrivateMemoButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.widthIn(min = 250.dp)
    ){
        Text(text = "Private Memo",
            color = Color.Black,
            fontSize = 20.sp)

    }
}

@Composable
fun StartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.widthIn(min = 250.dp)
    ){
        Text(text = "Start Memo",
            color = Color.Black,
            fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    AnytimeMemoTheme {
        //HomeScreen(
        //)
    }
}