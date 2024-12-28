package com.example.myschool.ui.screens

import android.content.Context
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
import androidx.compose.ui.unit.dp
import com.example.myschool.models.User
import com.example.myschool.models.UserType
import com.example.myschool.ui.assets.BottomNavigationBar
import com.example.myschool.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(navController: NavController, context: Context, viewModel: AuthViewModel = AuthViewModel(context)) {
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
            CenterAlignedTopAppBar(title = { Text("Accueil Élève") })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, userType = user?.userType ?: UserType.STUDENT)
        }
    ) { contentPadding ->
        // Contenu de l'écran pour l'élève
        Text(
            text = "Bienvenue, Élève !",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(contentPadding).padding(start = 16.dp)
        )
        // Autres composants comme les cours, les exercices, etc.
    }
}
