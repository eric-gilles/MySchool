package com.example.myschool.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Accueil Parent") })
        }
    ) { contentPadding ->
        Text(
            text = "Bienvenue sur l'accueil parent",
            modifier = Modifier.padding(contentPadding)
        )
    }
}
