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
import androidx.navigation.NavController
import com.example.traceapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController
) {
    val backgroundImage = painterResource(id = R.drawable.register_background)

    var userName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    fun validateAndRegister() {
        when {
            userName.isBlank() -> {
                validationMessage = "Username cannot be empty."
                showError = true
            }
            emailAddress.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> {
                validationMessage = "Enter a valid email address."
                showError = true
            }
            password.length < 8 -> {
                validationMessage = "Password must be at least 8 characters."
                showError = true
            }
            password != confirmPassword -> {
                validationMessage = "Passwords do not match."
                showError = true
            }
            else -> {
                navController.navigate("registerValidation") {
                    popUpTo("register") { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(showError) {
        if (showError) {
            delay(3000)

            showError = false
            validationMessage = ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF5E60CE)
                ),
                shape = RectangleShape
            )

            TextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text("Email address") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF5E60CE)
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
                    focusedIndicatorColor = Color(0xFF5E60CE)
                ),
                shape = RectangleShape
            )

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
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
                    focusedIndicatorColor = Color(0xFF5E60CE)
                ),
                shape = RectangleShape
            )

            Spacer(modifier = Modifier.height(160.dp))

            OutlinedButton(
                onClick = {
                    validateAndRegister()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                border = BorderStroke(2.dp, Color(0xFF292147)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF5E60CE),
                    containerColor = Color(0xFFF8F8F8)
                )
            ) {
                Text("Register")
            }

            if (showError && validationMessage.isNotEmpty()) {
                Text(validationMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account?", color = Color.White)
                TextButton(onClick = { navController.navigate("login") }) {
                    Text(text = "Sign In", color = Color.White)
                }
            }

        }
    }
}
