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
import com.android.redesocial.viewmodel.ThemeViewModel   // import já estava

@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(),
    themeViewModel: ThemeViewModel // ✅ agora vem de fora (mesmo do MainActivity)
) {

    val userId = authViewModel.getUidDoUsuario()

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
                opcoes = listOf(
                    "Alterar dados" to {
                        settingsViewModel.onEditAccount(navController, userId)
                    }
                )
            )

            Opcao(
                icon = Icons.Outlined.Lock,
                nome = "Privacidade",
                opcoes = listOf(
                    "Gerenciar privacidade" to { navController.navigate("construction") }
                )
            )

            Opcao(
                icon = Icons.Outlined.Notifications,
                nome = "Notificações",
                opcoes = listOf(
                    "Configurar notificações" to { navController.navigate("construction") }
                )
            )

            // ✅ Agora a troca de tema realmente reflete no app todo
            Opcao(
                icon = Icons.Outlined.BrightnessMedium,
                nome = "Tema",
                opcoes = listOf(
                    if (themeViewModel.isDarkTheme) {
                        "Mudar para tema claro" to { themeViewModel.toggleTheme() }
                    } else {
                        "Mudar para tema escuro" to { themeViewModel.toggleTheme() }
                    }
                )
            )

            Opcao(
                icon = Icons.Outlined.ExitToApp,
                nome = "Sair",
                opcoes = listOf(
                    "Encerrar sessão" to { settingsViewModel.onLogout(navController) }
                )
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
fun Opcao(icon: ImageVector, nome: String, opcoes: List<Pair<String, () -> Unit>>) {
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
                    .padding(bottom = 10.dp)
            ) {
                opcoes.forEach { (texto, acao) ->
                    Text(
                        text = "$texto",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable { acao() }
                    )
                }
            }
        }
    }
}
