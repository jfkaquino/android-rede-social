package com.android.redesocial.ui.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.RetrofitInstance
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.viewmodel.PostViewModel
import com.android.redesocial.viewmodel.PostViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PostScreen(
    // Recebendo o AuthViewModel (provavelmente da sua navegação)
    authViewModel: AuthViewModel,
    navController: NavController
) {
    // 1. Instanciar o ViewModel com a Factory
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(authViewModel)
    )

    // 2. Coletar os estados do ViewModel
    val label by viewModel.label.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val feedback by viewModel.postFeedback.collectAsState()

    // 3. Configurar o Snackbar para feedback
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Observa o 'feedback' do ViewModel
    LaunchedEffect(feedback) {
        feedback?.let {
            scope.launch {
                snackbarHostState.showSnackbar(message = feedback!!)
                viewModel.clearFeedback() // Limpa o feedback após mostrar
            }
        }
    }

    // 4. Configurar o seletor de mídia
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.onImageSelected(uri) // Informa o ViewModel
        }
    }

    Scaffold(
        topBar = { BarraSuperiorMenu("Novo post") },
        bottomBar = { BarraInferior(
            navController
        ) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Adiciona o Snackbar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp), // Adiciona padding geral
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Mostra a imagem selecionada
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(5.dp),
                painter = rememberAsyncImagePainter(imageUri), // Usa o imageUri do ViewModel
                contentDescription = "Imagem selecionada",
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = label, // Usa a label do ViewModel
                onValueChange = { viewModel.onLabelChanged(it) }, // Informa o ViewModel
                label = { Text("Legenda") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading // Desabilitado durante o carregamento
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                enabled = !isLoading
            ) {
                Text("Selecionar imagem")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.publishNewPost() // Chama o ViewModel
                },
                enabled = !isLoading // Desabilitado se não houver imagem ou se estiver carregando
            ) {
                Text("Postar")
            }

            // Indicador de carregamento
            if (isLoading) {
                Spacer(Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
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
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                modifier = Modifier
                    .size(35.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Perfil",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "Usuário",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = imagemUrl ?: "https://source.unsplash.com/random/800x800",
                contentDescription = "Imagem do post"
            )
        }

        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Perfil",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Comment,
                    contentDescription = "Comentários",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Compartilhar",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}