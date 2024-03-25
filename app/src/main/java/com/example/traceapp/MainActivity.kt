package com.example.traceapp

import RegisterValidationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.traceapp.userauthentication.LoginScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.traceapp.mainscreen.DashboardScreen
import com.example.traceapp.profile.ProfileScreen
import com.example.traceapp.userauthentication.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("registerValidation") { RegisterValidationScreen(navController)}
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    AppNavigation()
}
