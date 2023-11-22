package com.bastory.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bastory.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = remember {PreferencesManager(context)}
    val data = sharedPreferences.getData("firstName", "")
    val Route : String
    if(data == ""){
        Route = Registry.Route
    }else{
        Route = HomeScreen.Route
    }
    NavHost(navController = navController, startDestination = Route){
        composable(HomeScreen.Route){
            HomeScreen(navController)
        }
        composable(Registry.Route){
            Onboarding(navController)
        }
        composable(Profile.Route){
            ProfileScreen(navController)
        }
    }
}