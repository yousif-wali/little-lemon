package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()

                val sharedPreferences = getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
                var startDestination = Onboarding.route

                if (sharedPreferences.getBoolean("userRegistered", false)) {
                    startDestination = Home.route
                }

                NavHost(navController = navController, startDestination = startDestination) {
                    composable(Home.route) {
                        Home(applicationContext, navController, lifecycleScope)
                    }
                    composable(Onboarding.route) {
                        Onboarding(applicationContext, navController)
                    }
                    composable(Profile.route) {
                        Profile(applicationContext, navController)
                    }
                }
            }
        }
    }
}


