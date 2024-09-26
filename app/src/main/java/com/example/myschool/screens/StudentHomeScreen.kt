package com.example.myschool.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Accueil Élève") })
        }
    ) { contentPadding ->
        // Contenu de l'écran pour l'élève
        Text(
            text = "Bienvenue, Élève !",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(contentPadding)
        )
        // Autres composants comme les cours, les exercices, etc.
    }
}
