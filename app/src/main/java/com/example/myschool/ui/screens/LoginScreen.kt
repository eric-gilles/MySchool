package com.example.myschool.ui.screens

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschool.R
import com.example.myschool.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, context: Context, viewModel: AuthViewModel = AuthViewModel(context)) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MySchool - Connexion", style = MaterialTheme.typography.titleLarge) },
            )
        }
    ) { paddingValues ->
        // Add a vertical scroll modifier here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()), // Makes the screen scrollable
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo or App Icon
            Image(
                painter = painterResource(id = R.drawable.ic_logo_xl), // Replace with your logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Title
            Text(
                text = "Bon retour !",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Connectez-vous pour continuer votre apprentissage.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Adresse e-mail") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            ), // Replace with your icons
                            contentDescription = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Error Message
            AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Role Selection
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Se connecter en tant que :",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButtonWithLabel(
                        selected = selectedRole == "Parent",
                        onClick = { selectedRole = "Parent" },
                        label = "Parent"
                    )
                    RadioButtonWithLabel(
                        selected = selectedRole == "Student",
                        onClick = { selectedRole = "Student" },
                        label = "Élève"
                    )
                    RadioButtonWithLabel(
                        selected = selectedRole == "Teacher",
                        onClick = { selectedRole = "Teacher" },
                        label = "Professeur"
                    )
                }
            }

            // Login Button
            ElevatedButton(
                onClick = {
                    when {
                        email.isBlank() -> errorMessage = "L'adresse e-mail ne peut pas être vide"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            errorMessage = "Adresse e-mail invalide"
                        }
                        password.isBlank() -> errorMessage = "Le mot de passe ne peut pas être vide"
                        selectedRole.isBlank() -> errorMessage = "Veuillez sélectionner un rôle"
                        else -> {
                            errorMessage = ""
                            focusManager.clearFocus() // Clear focus to dismiss keyboard
                            viewModel.login(
                                email, password,
                                onSuccess = {
                                    viewModel.saveLoginState(email, selectedRole)
                                    when (selectedRole) {
                                        "Parent" -> navController.navigate("home/parent")
                                        "Teacher" -> navController.navigate("home/teacher")
                                        else -> navController.navigate("home/student")
                                    }
                                },
                                onFailure = { message -> errorMessage = message }
                            )
                        }
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Connexion", style = MaterialTheme.typography.labelLarge)
            }

            // Register and Forgot Password Links
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { navController.navigate("register") }) {
                    Text("Créer un compte", color = MaterialTheme.colorScheme.primary)
                }
                TextButton(onClick = {
                    if (email.isBlank()) errorMessage = "Veuillez entrer votre adresse e-mail"
                    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Adresse e-mail invalide"
                    } else viewModel.sendPasswordResetEmail(
                        email,
                        onSuccess = { Toast.makeText(context, "Un e-mail de réinitialisation a été envoyé", Toast.LENGTH_SHORT).show() },
                        onFailure = { message -> errorMessage = message }
                    )
                }) {
                    Text("Mot de passe oublié ?", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun RadioButtonWithLabel(selected: Boolean, onClick: () -> Unit, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
