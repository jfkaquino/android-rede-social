package com.android.redesocial.ui.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.data.cloud.Post
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.viewmodel.PostViewModel
import com.android.redesocial.viewmodel.PostViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PostScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(authViewModel)
    )

    // Coletar os estados atualizados
    val text by viewModel.text.collectAsState() // Renomeado de 'label'
    val isLoading by viewModel.loading.collectAsState()
    val feedback by viewModel.postFeedback.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(feedback) {
        feedback?.let {
            scope.launch {
                snackbarHostState.showSnackbar(message = it)
                viewModel.clearFeedback()
            }
        }
    }

    Scaffold(
        topBar = {
            BarraSuperiorMenu(
                title = "Criar post",
                navController = navController
            ) },
        bottomBar = { BarraInferior(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // --- Seção para Criar Novo Post ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = text, // Usa o 'text' do ViewModel
                    onValueChange = { viewModel.onTextChanged(it) }, // Chama 'onTextChanged'
                    label = { Text("O que está pensando?") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    minLines = 3 // Permite mais espaço para texto
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.publishNewPost()
                    },
                    enabled = !isLoading && text.isNotBlank() // Habilita se não estiver carregando E o texto não for vazio
                ) {
                    Text("Postar")
                }

                // Indicador de carregamento (apenas se estiver postando)
                if (isLoading) {
                    Spacer(Modifier.height(8.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}

/**
 * Um Composable simples para exibir um item de post (texto).
 */
@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Usuário",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Column {
                    Text(
                        text = post.ownerName ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = post.timestamp.toFriendlyDate("dd/MM/yy HH:mm"), // Formata a data
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = post.text ?: "", // O conteúdo do post
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Função utilitária simples para formatar o timestamp
fun Long.toFriendlyDate(s: String): String {
    val date = Date(this)
    val format = SimpleDateFormat(s, Locale.getDefault())
    return format.format(date)
}