package com.example.myschool.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.example.myschool.ui.assets.BottomNavigationBar

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
