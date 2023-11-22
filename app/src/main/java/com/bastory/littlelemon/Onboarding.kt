package com.bastory.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController) {
    val firstName = remember {mutableStateOf("")}
    val lastName  = remember {mutableStateOf("")}
    val email     = remember { mutableStateOf("") }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context)}

    Column (modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.width(200.dp))

        Box(modifier = Modifier.background(Color(0XFF495E57))
            .fillMaxWidth().height(100.dp), Alignment.Center){
            Text(text = "Let's get to know you", fontSize = 26.sp, color = Color.White)
        }

        Text("Personal information", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 30.dp, bottom = 30.dp, start = 10.dp).align(Alignment.Start))
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            OutlinedTextField(value = firstName.value, onValueChange = {firstName.value = it}, label = {Text("First Name")}, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = lastName.value, onValueChange = {lastName.value = it}, label = {Text("Last Name")}, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = email.value, onValueChange = {email.value = it}, label = {Text("Email")}, modifier = Modifier.fillMaxWidth(), singleLine = true)
        }
        Button(onClick={
            if(firstName.value == "" || lastName.value == "" || email.value == ""){
                Toast.makeText(context,"Registration unsuccessful.\nPlease enter all data.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Registration successfull", Toast.LENGTH_SHORT).show()
                preferencesManager.saveData("firstName", firstName.value)
                preferencesManager.saveData("lastName", lastName.value)
                preferencesManager.saveData("email", email.value)
                navController.navigate(HomeScreen.Route)
            }
        }, modifier = Modifier.fillMaxWidth()){
            Text("Register", color = Color.Black)
        }
    }

}