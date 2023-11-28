package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.Green
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Profile(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

    val firstName = remember { mutableStateOf(sharedPreferences.getString("firstName", "")) }
    val lastName = remember { mutableStateOf(sharedPreferences.getString("lastName", "")) }
    val email = remember { mutableStateOf(sharedPreferences.getString("email", "")) }

    Column() {
        Column {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 100.dp)
            )
        }
        Column(
            Modifier.padding(20.dp)
        ) {

            Text(text = "Personal Information", Modifier.padding(bottom = 20.dp))
            OutlinedTextField(value = firstName.value!!, onValueChange = { firstName.value = it},
                label = { Text("First Name") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            OutlinedTextField(value = lastName.value!!, onValueChange = { lastName.value = it},
                label = { Text("Last Name") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)

            )
            OutlinedTextField(value = email.value!!, onValueChange = { email.value = it},
                label = { Text("Email") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)

            )
            Button(onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(Onboarding.route)
            }, Modifier.fillMaxWidth().size(60.dp)) { Text(text = "Log Out")}
        }
    }




}