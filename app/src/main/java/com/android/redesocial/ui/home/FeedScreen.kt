package com.android.redesocial.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperior
import com.android.redesocial.ui.post.Post
import com.android.redesocial.viewmodel.AuthViewModel


@Composable
fun FeedScreen(
    authViewModel: AuthViewModel,
    user: com.google.firebase.auth.FirebaseUser
){

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

