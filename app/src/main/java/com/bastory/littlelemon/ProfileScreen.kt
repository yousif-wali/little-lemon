package com.bastory.littlelemon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context)}

    val firstName = remember {mutableStateOf(preferencesManager.getData("firstName", ""))}
    val lastName  = remember {mutableStateOf(preferencesManager.getData("lastName", ""))}
    val email     = remember { mutableStateOf(preferencesManager.getData("email", "")) }
    Column (modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.width(200.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), Alignment.Center){
        }

        Text("Personal information", fontWeight = FontWeight.Bold, modifier = Modifier
            .padding(top = 30.dp, bottom = 30.dp, start = 10.dp)
            .align(Alignment.Start))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            Text(firstName.value)
            Text(lastName.value)
            Text(email.value)
        }
        Button(onClick={
            if(firstName.value == "" || lastName.value == "" || email.value == ""){
                Toast.makeText(context,"Registration unsuccessful.\nPlease enter all data.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Logout successfull", Toast.LENGTH_SHORT).show()
                preferencesManager.reset()
                navController.navigate(Registry.Route)
            }
        }, modifier = Modifier.fillMaxWidth()){
            Text("Log out", color = Color.Black)
        }
    }

}