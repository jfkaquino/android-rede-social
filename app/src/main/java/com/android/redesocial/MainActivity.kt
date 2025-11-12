package com.android.redesocial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.redesocial.ui.account.LoginScreen
import com.android.redesocial.ui.account.SignupScreen
import com.android.redesocial.ui.post.FeedScreen
import com.android.redesocial.ui.post.PostScreen
import com.android.redesocial.ui.profile.ProfileScreen
import com.android.redesocial.ui.settings.SettingsScreen
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
            startDestination = if (user != null) "feed" else "login"
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

            composable("feed") {

                if(user != null) {
                    FeedScreen(
                        authViewModel = authViewModel,
                        navController = navController
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

            composable("makePost") {

                if(user != null) {
                    PostScreen(
                        authViewModel = authViewModel,
                        navController = navController,
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

            composable("profile") {

                if(user != null) {
                    ProfileScreen(
                        authViewModel = authViewModel,
                        navController = navController,
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

            composable("settings") {

                if(user != null) {
                    SettingsScreen(
                        authViewModel = authViewModel,
                        navController = navController,
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