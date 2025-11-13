package com.android.redesocial.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperior
import com.android.redesocial.ui.post.PostItem
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.viewmodel.PostViewModel
import com.android.redesocial.viewmodel.PostViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun FeedScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(authViewModel)
    )

    val posts by viewModel.postsList.collectAsState()
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

    LaunchedEffect(Unit) {
        viewModel.loadGlobalFeed()
    }

    Scaffold(
        topBar = { BarraSuperior(navController) },
        bottomBar = { BarraInferior(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(posts) { post ->
                    PostItem(post = post, navController = navController)
                }
            }
        }
    }
}