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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    TopAppBar(
        modifier = Modifier.padding(5.dp),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.nome),
                contentDescription = "Nome",
                modifier = Modifier
                    .height(80.dp)
                    .padding(start = 2.dp),
                contentScale = ContentScale.Fit
            )
        },
        title = { Text("") },
        actions = {
            Row {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Notificações")
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Chat")
                }
            }
        }
    )
}

@Composable
fun BarraInferior() {
    BottomAppBar {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BarraInferiorBotao(Icons.Default.Home)
            BarraInferiorBotao(Icons.Default.Search, { })
            BarraInferiorBotao(Icons.Default.AddCircle)
            BarraInferiorBotao(Icons.Default.Star)
            BarraInferiorBotao(Icons.Default.AccountCircle)
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