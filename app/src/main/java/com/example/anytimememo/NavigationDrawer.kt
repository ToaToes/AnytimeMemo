package com.example.anytimememo

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.cupcake.R
//import com.example.cupcake.data.DataSource
import com.example.anytimememo.ui.theme.AnytimeMemoTheme

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
){
    Box (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimary)){
        //modifier = Modifier.background(Color.White)
        //Box(modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary))
        LazyColumn(modifier) {
            //import androidx.compose.foundation.lazy.items !!!
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onItemClick(item)
                        }
                        .padding(16.dp)
                        //.background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Icon(imageVector = item.icon, contentDescription = "Menu Items")

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        style = itemTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerBodyPreview(){
    AnytimeMemoTheme {
        val sampleItems = listOf(
            MenuItem("1","Home", "Home", Icons.Default.Home),
            MenuItem("2", "Setting", "Setting", Icons.Default.Settings)
        )
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            DrawerBody(
                items = sampleItems,
                itemTextStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                onItemClick = {item ->}

            )
        }
    }
}