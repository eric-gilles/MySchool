package com.example.myschool.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RegistrationScreen(navController: NavController, context: Context, viewModel: AuthViewModel = AuthViewModel()) {
    var name by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MySchool - Inscription", style = MaterialTheme.typography.titleLarge) },
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Logo or App Icon
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_xl),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Title
                Text(
                    text = "Créer un compte",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Rejoignez MySchool et commencez\n à apprendre dès maintenant !",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Name Input
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                // Firstname Input
                OutlinedTextField(
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = { Text("Prénom") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
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
                                ),
                                contentDescription = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Confirm Password Input
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmer le mot de passe") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                ),
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

                //Spacer(modifier = Modifier.height(2.dp))

                // Register Button
                ElevatedButton(
                    onClick = {
                        when {
                            name.isBlank() -> errorMessage = "Le nom ne peut pas être vide"
                            firstname.isBlank() -> errorMessage = "Le prénom ne peut pas être vide"
                            email.isBlank() -> errorMessage = "L'adresse e-mail ne peut pas être vide"
                            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                errorMessage = "Adresse e-mail invalide"
                            }
                            password.isBlank() -> errorMessage = "Le mot de passe ne peut pas être vide"
                            password != confirmPassword -> errorMessage = "Les mots de passe ne correspondent pas"
                            else -> {
                                errorMessage = ""
                                focusManager.clearFocus() //remove the focus from the text fields
                                viewModel.registerParent(
                                    name, firstname, email, password,
                                    onSuccess = {
                                        navController.navigate("login")
                                        viewModel.sendEmailVerification(
                                            email,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Validez votre compte en cliquant sur le lien envoyé à votre adresse e-mail",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            },
                                            onFailure = { message -> errorMessage = message }
                                        )
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
                    Text(text = "S'inscrire", style = MaterialTheme.typography.labelLarge)
                }

                // Back to Login Link
                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Vous avez déjà un compte? Connexion", color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
