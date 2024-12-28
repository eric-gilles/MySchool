package com.example.myschool.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschool.models.User
import com.example.myschool.models.UserType
import com.example.myschool.ui.assets.BottomNavigationBar
import com.example.myschool.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel = AuthViewModel()) {
    val context = LocalContext.current
    val userId = remember { viewModel.getCurrentUserId() }
    var user by remember { mutableStateOf<User?>(null) }
    var link by remember { mutableStateOf("") }
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
            CenterAlignedTopAppBar(
                title = { Text("Mon Profil", style = MaterialTheme.typography.titleLarge) }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, userType = user?.userType ?: UserType.PARENT)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (user != null) {
                    Text(
                        text = "Nom: ${user?.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Prénom: ${user?.firstname}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Email: ${user?.email}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Type d'utilisateur: ${user?.userType?.toString()?.lowercase()?.replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (user?.userType == UserType.PARENT) {
                        Text(
                            text = "Nombre d'enfants: ${user?.children?.size ?: 0}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = link,
                        onValueChange = { link = it },
                        label = { Text("Ajouter un lien (Facebook, Twitter, etc.)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Uri),
                        modifier = Modifier.fillMaxWidth()
                    )
                    ElevatedButton(
                        onClick = {
                            if (link.isNotBlank()) {
                                // Logic to add the link to Firestore
                                Toast.makeText(context, "Lien ajouté : $link", Toast.LENGTH_SHORT).show()
                                link = ""
                            } else {
                                Toast.makeText(context, "Veuillez entrer un lien valide", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Ajouter le lien")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ElevatedButton(
                        onClick = {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Text(text = "Se déconnecter")
                    }
                } else if (errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
