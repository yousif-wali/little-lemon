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
fun Onboarding(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    Column() {
        Column {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 100.dp)
            )
            Row(
                modifier = Modifier.background(Green).padding(50.dp).fillMaxWidth(),
                ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Let's get to know you",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(
            Modifier.padding(20.dp)
        ) {

            Text(text = "Personal Information", Modifier.padding(bottom = 20.dp))
            OutlinedTextField(value = firstName.value, onValueChange = { firstName.value = it},
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            OutlinedTextField(value = lastName.value, onValueChange = { lastName.value = it},
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)

            )
            OutlinedTextField(value = email.value, onValueChange = { email.value = it},
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)

            )
            Button(onClick = {
                if (firstName.value.isNotEmpty() && lastName.value.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                    sharedPreferences.edit()
                        .putString("firstName", firstName.value)
                        .putString("lastName", lastName.value)
                        .putString("email", email.value)
                        .putBoolean("userRegistered", true)
                        .apply()

                    navController.navigate(Home.route)
                } else {
                    Toast.makeText(context, "Invalid Details", Toast.LENGTH_SHORT).show()
                }
            }, Modifier.fillMaxWidth().size(60.dp)) { Text(text = "Register")}
        }
    }




}

//@Preview(showBackground = true)
//@Composable
//fun OnboardingPreview() {
//    LittleLemonTheme {
//        Onboarding(context, navController = rememberNavController())
//    }
//}