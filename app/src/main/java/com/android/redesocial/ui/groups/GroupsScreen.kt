package com.android.redesocial.ui.groups

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.redesocial.ui.BarraSuperiorOpcao
import com.android.redesocial.viewmodel.GroupsViewModel


@Composable
fun GroupsScreen(navController: androidx.navigation.NavController,viewModel: GroupsViewModel = viewModel()) {

    Scaffold(
        topBar = { BarraSuperiorOpcao(navController, "Grupos") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                viewModel.grupos.forEachIndexed { index, grupo ->
                    Group(
                        imageResId = grupo.imageResId,
                        nome = grupo.nome,
                        participantes = grupo.participantes,
                        isMember = grupo.entrou,
                        onEntrarClick = { viewModel.entrarNoGrupo(index) },
                        onChatClick = { navController.navigate("construction") },
                        onVerParticipantesClick = { navController.navigate("construction") }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun Group(@DrawableRes imageResId: Int, nome: String, participantes: Int, isMember: Boolean, onEntrarClick: () -> Unit = {}, onChatClick: () -> Unit = {}, onVerParticipantesClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(398.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Nome",
                modifier = Modifier
                    .height(65.dp)
                    .padding(start = 5.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$participantes participantes",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Ver participantes",
                    color = Color(0xFF0000EE),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { onVerParticipantesClick() }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = onEntrarClick,
                    modifier = Modifier.width(70.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    val texto = if (isMember) "Sair" else "Entrar"
                    Text(texto, style = MaterialTheme.typography.labelSmall)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                    contentDescription = "Chat",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onChatClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MostrarTelaPreview() {
    val fakeNavController = androidx.navigation.compose.rememberNavController()
    GroupsScreen(navController = fakeNavController)
}