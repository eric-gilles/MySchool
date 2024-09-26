package com.example.myschool.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(courseId: String, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Détails du Cours") })
        }
    ) { contentPadding ->
        // Logique pour récupérer et afficher les détails du cours
        Text(
            text = "Détails du cours pour l'ID : $courseId",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(contentPadding)
        )
        // Afficher la vidéo, les documents, les exercices, etc.
    }
}
