package com.example.myschool.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.myschool.models.User
import com.example.myschool.models.UserType
import com.example.myschool.ui.assets.BottomNavigationBar
import com.example.myschool.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(navController: NavController, context: Context, viewModel: AuthViewModel = AuthViewModel(context)) {
    val userId = remember { viewModel.getCurrentUserId() }
    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Fetch user data
    LaunchedEffect(userId) {
        viewModel.fetchUserData(
            userId = userId,
            onSuccess = { fetchedUser -> user = fetchedUser },
            onFailure = { message -> errorMessage = message }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Détails du Cours") })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, userType = user?.userType ?: UserType.PARENT)
        }
    ) { contentPadding ->
        // Logique pour récupérer et afficher les détails du cours
        Text(
            text = "Détails du cours :",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(contentPadding).padding(start = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "à venir...",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(top = 50.dp)
            )
        }
        // Afficher la vidéo, les documents, les exercices, etc.
    }
}
