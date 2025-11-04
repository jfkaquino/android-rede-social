package com.android.redesocial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.nome),
                contentDescription = "Nome",
                modifier = Modifier
                    .height(65.dp),
                contentScale = ContentScale.Fit
            )
        },
        title = { },
        actions = {
            Row {
                IconButton(
                    modifier = Modifier
                        .size(30.dp),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(),
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificações"
                    )
                }
                Spacer(Modifier.width(12.dp))
                IconButton(
                    modifier = Modifier
                        .size(30.dp),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(),
                        imageVector = Icons.AutoMirrored.Outlined.Message,
                        contentDescription = "Chat"
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
        }
    )
}

@Preview
@Composable
fun BarraInferior() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BarraInferiorBotao(Icons.Outlined.Home)
            BarraInferiorBotao(Icons.Outlined.Search)
            BarraInferiorBotao(Icons.Outlined.AddCircleOutline)
            BarraInferiorBotao(Icons.Outlined.SportsEsports)
            BarraInferiorBotao(Icons.Outlined.AccountCircle)
        }
    }
}

@Composable
private fun BarraInferiorBotao(
    Icone: ImageVector,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .size(30.dp)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icone,
                contentDescription = "",
            )
        }
        Spacer(Modifier.height(10.dp))
    }
}