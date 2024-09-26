package com.example.myschool.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschool.screens.*

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registration") { RegistrationScreen(navController) }
        composable("parent_home") { ParentHomeScreen(navController) }
        composable("student_home") { StudentHomeScreen(navController) }
        composable("course/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            CourseScreen(courseId ?: "", navController)
        }
        composable("profile") { ProfileScreen(navController) }
    }
}
