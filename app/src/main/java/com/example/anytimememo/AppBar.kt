package com.example.anytimememo

import androidx.compose.foundation.CombinedClickableNode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.anytimememo.ui.theme.AnytimeMemoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            //stringResource(id = R.string.app_name)
            Text(text = "")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            //navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            //actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        //backgroundColor = MaterialTheme.colors.primary,
        //change anytime
        //colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        //contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            //Navigate Back Arrow option
            /*
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
             */
            IconButton(onClick = onNavigationIconClick){
                //
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle Drawer"
                )
            }

        }
    )
}


@Preview
@Composable
fun AppBarPreview(){
    AnytimeMemoTheme {
        AppBar(onNavigationIconClick = {})
    }
}