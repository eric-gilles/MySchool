package com.example.myschool.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myschool.models.Course
import com.example.myschool.ui.assets.BottomNavItem
import com.example.myschool.ui.assets.BottomNavigationBar
import com.example.myschool.viewmodels.TeacherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Accueil Professeur") })
        }
    ) { contentPadding ->
        Text(
            text = "Bienvenue sur l'accueil Professeur",
            modifier = Modifier.padding(contentPadding)
        )
    }

}
