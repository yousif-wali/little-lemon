package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController

@Composable
fun Home(context: Context, navController: NavController, lifecycleScope: LifecycleCoroutineScope) {
    Column {
        TopAppBar(navController)
        UpperPanel()
        LowerPanel(context = context, lifecycleScope = lifecycleScope)
    }
}