package com.android.redesocial.ui.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            BarraSuperiorMenu(
                title = "Configurações",
                navController = navController
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Opcao(
                icon = Icons.Outlined.Person,
                nome = "Conta",
                opcoes = listOf("Alterar nome", "Alterar e-mail", "Alterar senha")
            )

            Opcao(Icons.Outlined.Lock,
                nome = "Conta",
                opcoes = listOf("Alterar nome", "Alterar e-mail", "Alterar senha")
            )

            Opcao(Icons.Outlined.Notifications,
                nome = "Conta",
                opcoes = listOf("Alterar nome", "Alterar e-mail", "Alterar senha")
            )

            Opcao(Icons.Outlined.BrightnessMedium,
                nome = "Tema",
                opcoes = listOf("Alterar nome", "Alterar e-mail", "Alterar senha")
            )

            Opcao(Icons.Outlined.ExitToApp,
                nome = "Sair",
                opcoes = listOf("Alterar nome", "Alterar e-mail", "Alterar senha")
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Opcao(icon: ImageVector, nome: String, opcoes: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "rotation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.width(300.dp))

        Row(
            modifier = Modifier
                .width(300.dp)
                .height(56.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(30.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Text(nome)
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Outlined.ArrowDropDownCircle,
                contentDescription = "Expandir",
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .padding(start = 40.dp, bottom = 10.dp)
            ) {
                opcoes.forEach { opcao ->
                    Text(
                        text = "• $opcao",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
