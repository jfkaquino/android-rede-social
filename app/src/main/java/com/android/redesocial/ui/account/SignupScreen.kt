package com.android.redesocial.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.redesocial.viewmodel.AuthViewModel

@Composable
fun SignupScreen(authViewModel: AuthViewModel, onNavigateToLogin: () -> Unit, navController: NavController? = null, userId: String? = null
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by authViewModel.loading.collectAsStateWithLifecycle()
    val userData = authViewModel.userState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        authViewModel.uiEvent.collect { event ->
            when (event) {
                is AuthViewModel.UiEvent.NavigateToSettings -> {
                    navController?.popBackStack("settings", inclusive = false)
                }
            }
        }
    }

    LaunchedEffect(userId) {
        if (userId != null && userData != null) {
            name = userData.displayName ?: ""
            email = userData.email ?: ""
            password = ""
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (userId != null) "Editar dados" else "Criar conta",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(160.dp),
                onClick = {
                    if (userId != null) {
                        authViewModel.updateUserData(name, email, navController)
                    } else {
                        authViewModel.signUp(email, password, name, navController)
                    }
                }
            ) {
                Text(if (userId != null) "Salvar alterações" else "Criar conta")
            }

            if (userId == null) {
                Spacer(modifier = Modifier.height(4.dp))
                TextButton(onClick = onNavigateToLogin) { Text("Fazer login") }
            }

            if (isLoading) CircularProgressIndicator()
        }
    }
}