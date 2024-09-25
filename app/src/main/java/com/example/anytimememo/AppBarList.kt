package com.example.anytimememo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anytimememo.ui.theme.AnytimeMemoTheme
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.anytimememo.ui.note.MemoScreen
import com.example.anytimememo.ui.notelist.MemoListScreen
import com.example.anytimememo.ui.notelist.NoteListViewModel
import com.example.anytimememo.ui.utility.Route
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.navigation
import com.example.anytimememo.ui.note.NoteViewModel
import com.example.anytimememo.ui.note.PrivateMemoScreen
import com.example.anytimememo.ui.utility.UiEvent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collect

//top bar items
val menuItems = listOf(
    MenuItem(
        id = "Home",
        title = "Home",
        contentDescription = "Navigate to Home page",
        icon = Icons.Default.Home
    ),
    MenuItem(
        id = "Logging",
        title = "Logging",
        contentDescription = "Get to Logging page",
        icon = Icons.AutoMirrored.Filled.Login
    ),
    MenuItem(
        id = "Help",
        title = "Help",
        contentDescription = "Get to Help page",
        icon = Icons.Default.Info
    ),
    MenuItem(
        id = "Setting",
        title = "Setting",
        contentDescription = "Get to Setting page",
        icon = Icons.Default.Settings
    ),
    MenuItem(
        id = "Sign out",
        title = "Sign out",
        contentDescription = "Sign out",
        icon = Icons.AutoMirrored.Filled.Logout
    ),
)

//ViewModel to update the sate of the city
class CityViewModel : ViewModel() {
    var cityDisplay = mutableStateOf<String?>(null)
        private set
    var weather = "20 degrees, Mostly Cloud"
    fun updateCityName(newCity: String?) {
        if (newCity != null) {
            cityDisplay.value = newCity
        } else{
            cityDisplay.value = "Unknown City"
        }
    }
}

@Composable
fun AppBarList(
    authViewModel: AuthViewModel,
    database: DatabaseReference
){
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    LaunchedEffect(authState.value){
        when(authState.value) {
            is AuthState.SignOut -> Toast.makeText(
                context,
                "Signing Out",
                Toast.LENGTH_SHORT
            ).show()
            is AuthState.Unauthenticated -> Toast.makeText(
                context,
                "Unauthenticated",
                Toast.LENGTH_SHORT
            ).show()
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        // wont drag away with the sidebar
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            //change the position of the drawer in case
            //https://stackoverflow.com/questions/72859179/jetpack-compose-how-to-modify-the-width-of-drawer-in-modalnavigationdrawer
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ){
                    //Main content of the drawer
                    Column(
                        modifier = Modifier
                            .weight(1f) // takes available space
                            .padding(bottom = 72.dp) //padding to make room for footer
                    ){
                        DrawerBody(menuItems, onItemClick = { item -> //Forgot the item ->
                            if(item.id == "Sign out"){
                                if(AuthState.Authenticated == authState.value)
                                    authViewModel.signout()
                                else
                                    authViewModel.unauthenticate()
                            }else {
                                navController.navigate(item.id) {
                                    //To get back
                                    navController.graph.startDestinationRoute?.let { id ->
                                        popUpTo(id) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            scope.launch { drawerState.close() }
                        })
                    }
                    // Footer content for display location
                    FooterLocation()
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        onNavigationIconClick =  {
                            scope.launch{drawerState.open()}
                        }
                    )
                },
                content = { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        ComposeNavigation(
                            navController = navController,
                            authViewModel,
                            database
                        )
                    }
                },
            )
        }
    )
}

//https://medium.com/@rahnuma.sharib786/how-to-design-navigation-drawer-in-jetpack-compose-15ea323dd18f
//The drawer body needs a list of MenuItem as a parameter and it will create rows as many times as items are there in a list.
//Each row contain a icon and then space and then a text from left
/*@Composable
fun DrawerBody(
    items : List<MenuItem>,
    drawerState: DrawerState,
    navController: NavController,
    modifier: Modifier,
    //itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit,
    //selected: Boolean
){
    LazyColumn(modifier){
        items(items){ item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clickable {
                    onItemClick(item)
                }
            ){
                Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                Text(
                    text = item.title,
                    //style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}*/


//NavGraph
@Composable
fun ComposeNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    database: DatabaseReference
){
    //important
    //val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home")
    {

        composable("Home") {
            HomeScreen(
                navController,
                authViewModel
            )
        }
        composable("Setting") {
            SettingPage()
        }
        composable("Memo") {
            //MemoListScreen()
            val viewModel = hiltViewModel<NoteListViewModel>()
            val noteList by viewModel.noteList.collectAsStateWithLifecycle()

            MemoListScreen(
                noteList = noteList,
                onNoteClick = {
                    navController.navigate(
                        Route.NOTE.replace("{id}",it.id.toString())
                    )
                },
                onAddNoteClick = {
                    navController.navigate(Route.NOTE)
                }
            )
        }
        navigation(
            startDestination = Route.NOTE_LIST,
            route = "MemoList"
        ){
            composable(route = Route.NOTE){
                val viewModel = hiltViewModel<NoteViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = true){
                    viewModel.event.collect { event ->
                        when (event) {
                            is UiEvent.NavigateBack -> {
                                navController.popBackStack()
                            }
                            else -> Unit
                        }
                    }
                }
                MemoScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
        composable("Help") {
            HelpPage()
        }
        composable("Logging") {
            LoginPage(
                navController
            )
        }
        composable("Register") {
            RegistrationScreen(
                authViewModel,
                navController
            )
        }
        composable("LogIN") {
            LoginScreen(
                authViewModel,
                navController
            )
        }
        composable("PrivateMemo") {
            PrivateMemoScreen(
                navController,
                database
            )
        }
        /*
        composable(NavDrawerItem.Location.route) {
            LocationScreen()
        }
        composable(NavDrawerItem.Preferences.route) {
            PreferencesScreen()
        }
        */
    }

}

//Help function
/*@Composable
fun <T> NavBackStackEntry.sharedViewModel(navController: NavController) : T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}*/

//Location Footer
@Composable
fun FooterLocation(cityViewModel: CityViewModel = viewModel()){

    val context = LocalContext.current
    //val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val locationServicesEnabled by remember { mutableStateOf((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) }

    //not going into the detail
    /*LaunchedEffect(Unit) {
        //why???
        locationServicesEnabled = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }*/

    val cityName by remember { cityViewModel.cityDisplay }
    var cityDisplay by remember { mutableStateOf(cityName) }
    val weather by remember { mutableStateOf(cityViewModel.weather) }
    var weatherDisplay by remember { mutableStateOf(weather) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        if(!locationServicesEnabled){
            cityDisplay = "Unknown City"
            weatherDisplay = "Unknown Weather"
        }

        BasicText(
            text = "Location: $cityName",
            modifier = Modifier
                .background(Color.White),
            style = MaterialTheme.typography.bodyMedium
        )
        BasicText(
            text = "Weather: $weatherDisplay",
            modifier = Modifier
                .background(Color.White),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarListPreview() {
    val authViewModel = AuthViewModel()
    val database = FirebaseDatabase.getInstance().reference
    AnytimeMemoTheme {
        AppBarList(authViewModel, database)
    }
}