package com.android.redesocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SettingsViewModel : ViewModel() {

    fun onEditAccount(navController: NavController, userId: String?) {
        navController.navigate("TelaCadastro/$userId")
    }

    fun onLogout(navController: NavController) {
        navController.navigate("login") {
            popUpTo(0)
        }
    }

    fun onPrivacy(navController: NavController) {
        navController.navigate("privacy")
    }

    fun onTheme(navController: NavController) {
        navController.navigate("theme")
    }

    fun onNotifications(navController: NavController) {
        navController.navigate("notifications")
    }
}