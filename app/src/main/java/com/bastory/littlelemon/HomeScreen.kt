package com.bastory.littlelemon

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Text("Welcome")
    Button(onClick={
        navController.navigate(Profile.Route)
    }){
        Text("Profile")
    }
}