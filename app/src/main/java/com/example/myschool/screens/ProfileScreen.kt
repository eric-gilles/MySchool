package com.example.myschool.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mon Profil") })
        }
    ) { contentPadding ->
        // Logique pour afficher et gérer le profil de l'utilisateur
        Text(
            text = "Gérer mon profil",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(contentPadding)
        )
    }
}
