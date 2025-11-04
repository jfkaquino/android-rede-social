package com.android.redesocial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.redesocial.ui.account.LoginScreen
import com.android.redesocial.ui.account.SignupScreen
import com.android.redesocial.ui.home.Feed
import com.android.redesocial.ui.theme.RedeSocialTheme
import com.android.redesocial.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedeSocialTheme {
                AppNavigation(authViewModel)
            }
        }
    }

    @Composable
    fun AppNavigation(authViewModel: AuthViewModel = AuthViewModel()) {

        val navController = rememberNavController()
        val user by authViewModel.userState.collectAsStateWithLifecycle()
        val isLoading by authViewModel.loading.collectAsStateWithLifecycle()
        val feedbackMsg by authViewModel.authFeedback.collectAsStateWithLifecycle()

        LaunchedEffect(feedbackMsg) {
            feedbackMsg?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                authViewModel.clearFeedback()
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (user != null) "home" else "login"
        ) {
            composable("signup") {
                SignupScreen(
                    authViewModel = authViewModel,
                    onNavigateToLogin = { navController.navigate("login") }
                )
            }

            composable("login") {
                LoginScreen(
                    authViewModel = authViewModel,
                    onNavigateToSignUp = { navController.navigate("signup") }
                )
            }

            composable("home") {
                if(user != null) {
                    Feed(
                        authViewModel = authViewModel,
                        user = user!!
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login"){
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

        }
    }
}