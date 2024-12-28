package com.example.myschool

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.myschool.navigation.AppNavHost
import com.example.myschool.ui.theme.MySchoolTheme
import com.example.myschool.viewmodels.AuthViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val authViewModel = AuthViewModel(this)
        val sharedPreferences = this.getSharedPreferences("MySchoolPrefs", Context.MODE_PRIVATE)
        val initialRoute = determineInitialRoute(authViewModel, sharedPreferences)

        setContent {
            MySchoolTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(navController = navController, context = this, startDestination = initialRoute)
                }
            }
        }
    }

    // Détermine la route initiale de l'application
    private fun determineInitialRoute(authViewModel: AuthViewModel, sharedPreferences: SharedPreferences): String {
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        if (authViewModel.isUserLoggedIn()) {
            val userType = authViewModel.getUserInfo().second
            return when (userType) {
                "Parent" -> "home/parent"
                "Student" -> "home/student"
                "Teacher" -> "home/teacher"
                else -> "login"
            }
        }else {
            if (isFirstTime) {
                sharedPreferences.edit()?.putBoolean("isFirstTime", false)?.apply()
                return "register"
            } else {
                return "login"
            }
        }
    }
}

