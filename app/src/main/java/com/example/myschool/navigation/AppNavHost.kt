package com.example.myschool.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschool.ui.screens.CourseScreen
import com.example.myschool.ui.screens.LoginScreen
import com.example.myschool.ui.screens.ParentHomeScreen
import com.example.myschool.ui.screens.ProfileScreen
import com.example.myschool.ui.screens.RegistrationScreen
import com.example.myschool.ui.screens.StudentHomeScreen
import com.example.myschool.ui.screens.TeacherHomeScreen

@Composable
fun AppNavHost(navController: NavHostController, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MySchoolPrefs", Context.MODE_PRIVATE)
    val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

    val startDestination = if (isFirstTime) {
        sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
        "register"
    } else {
        "login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("register") { RegistrationScreen(navController, context) }
        composable("login") { LoginScreen(navController, context) }

        composable("home/parent") { ParentHomeScreen(navController) }
        composable("home/student") { StudentHomeScreen(navController) }
        composable("home/teacher") { TeacherHomeScreen(navController) }

        //composable("courses") { CoursesScreen(navController) }
        composable("course/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            CourseScreen(courseId ?: "", navController)
        }

        composable("profile") { ProfileScreen(navController) }
    }
}
