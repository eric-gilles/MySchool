package com.example.myschool.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschool.viewmodels.AuthViewModel

@Composable
fun RegistrationScreen(navController: NavController, viewModel: AuthViewModel = AuthViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                viewModel.register(email, password,
                    onSuccess = {
                        // Naviguer vers l'écran principal ou un écran de confirmation
                        navController.navigate("home")
                    },
                    onFailure = { message ->
                        // Gérer l'affichage de l'erreur
                        errorMessage = message
                    }
                )
            }
        ) {
            Text("S'inscrire")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}

