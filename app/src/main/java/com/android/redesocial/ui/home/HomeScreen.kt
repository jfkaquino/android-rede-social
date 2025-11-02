package com.android.redesocial.ui.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperior
import com.android.redesocial.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
@Preview
fun Feed(){

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(10) {
                Post()
            }
        }
    }
}

@Composable
fun Post() {
    val clientId = "WoI_pqstOEXe6jOI1iN5HDyWEbjxqoP2MYwxa1MFl5A"

    var imagemUrl by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (imagemUrl == null) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitInstance.api.getRandomPhoto(clientId)
                    imagemUrl = response.urls.regular
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(32.dp)
                )
            }
            Text("Usuário", fontWeight = FontWeight.Bold)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = imagemUrl ?: "https://source.unsplash.com/random/800x800",
                contentDescription = "Imagem do post",
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(27.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Comentários",
                    modifier = Modifier.size(27.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Compartilhar",
                    modifier = Modifier.size(27.dp)
                )
            }
        }
    }
}

