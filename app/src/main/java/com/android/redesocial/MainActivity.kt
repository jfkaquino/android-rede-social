package com.android.redesocial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.redesocial.ui.account.LoginScreen
import com.android.redesocial.ui.account.SignupScreen
import com.android.redesocial.ui.post.PostScreen
import com.android.redesocial.ui.profile.ProfileScreen
import com.android.redesocial.ui.settings.SettingsScreen
import com.android.redesocial.ui.theme.RedeSocialTheme
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.ui.construction.ConstructionScreen
import com.android.redesocial.ui.feed.FeedScreen
import com.android.redesocial.ui.games.GamesScreen
import com.android.redesocial.ui.games.XadrezScreen
import com.android.redesocial.ui.groups.GroupsScreen
import com.android.redesocial.viewmodel.ThemeViewModel // 👈 import do tema

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by remember { derivedStateOf { themeViewModel.isDarkTheme } }
            RedeSocialTheme(darkTheme = isDarkTheme) {
                AppNavigation(authViewModel = authViewModel, themeViewModel = themeViewModel)
            }
        }
    }

    @Composable
    fun AppNavigation(
        authViewModel: AuthViewModel = AuthViewModel(),
        themeViewModel: ThemeViewModel = viewModel()
    ) {

        val navController = rememberNavController()
        val user by authViewModel.userState.collectAsStateWithLifecycle()
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
                if (user != null) {
                    FeedScreen(
                        authViewModel = authViewModel,
                        navController = navController
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                }
            }

            composable("makePost") {
                if (user != null) {
                    PostScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                }
            }

            composable("profile") {
                if (user != null) {
                    ProfileScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                }
            }

            composable("profile/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")

                if(user != null) {
                    ProfileScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                        userId = userId!!
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                }
            }

            composable("settings") {
                if (user != null) {
                    SettingsScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                        settingsViewModel = viewModel(),
                        themeViewModel = themeViewModel
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                }
            }

            composable("TelaCadastro") {
                SignupScreen(
                    authViewModel = authViewModel,
                    onNavigateToLogin = { navController.navigate("login") }
                )
            }

            composable("TelaCadastro/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")
                SignupScreen(
                    authViewModel = authViewModel,
                    onNavigateToLogin = { navController.navigate("login") },
                    navController = navController,
                    userId = userId
                )
            }

            composable("construction") {
                ConstructionScreen(navController = navController)
            }

            composable("groups") { GroupsScreen(navController = navController) }

            composable("xadrez") { XadrezScreen() }

            composable("games") { GamesScreen(navController = navController) }
        }
    }
}
