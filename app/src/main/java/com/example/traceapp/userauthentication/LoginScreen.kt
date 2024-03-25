package com.example.traceapp.userauthentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.traceapp.R
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {

    val backgroundImage = painterResource(id = R.drawable.login___1_)

    val userStandardEmail = "user@example.com"
    val userStandardPassword = "12345678"
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Login form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(220.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email address") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF292147)
                ),
                shape = RectangleShape
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible)
                        painterResource(R.drawable.baseline_visibility_24)
                    else
                        painterResource(R.drawable.baseline_visibility_off_24)

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(painter = image, contentDescription = "Toggle password visibility")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF292147)
                ),
                shape = RectangleShape
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { /* TODO: it will be implemented during phase 2 */ }
            ) {
                Text("Forgot Password?", color = Color(0xFF292147))
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showError) {
                LaunchedEffect(Unit) {
                    delay(4000)
                    showError = false
                }
            }

            OutlinedButton(
                onClick = {
                    if (email == userStandardEmail && password == userStandardPassword) {
                        showError = false
                        navController.navigate("dashboard")
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 8.dp, end = 8.dp),
                border = BorderStroke(2.dp, Color(0xFF292147)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF5E60CE),
                    containerColor = Color(0xFFF8F8F8)
                )
            ) {
                Text("Log in")
            }

            if (showError) {
                Text(
                    "Login failed. Please check your credentials.",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            OutlinedButton(
                onClick = {navController.navigate("register")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 40.dp, end = 40.dp),
                border = BorderStroke(2.dp, Color(0xFF292147)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF5E60CE),
                    containerColor = Color(0xFFF8F8F8)
                )
            ) {
                Text("Create")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Don't Have an Account?", color = Color.White)
        }
    }
}
