package com.android.redesocial.ui.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.redesocial.BarraSuperiorMenu

@Composable
@Preview
fun Settings(){

    Scaffold(
        topBar = { BarraSuperiorMenu("Jogos") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Opcao()

            Opcao()
            Opcao()
            Opcao()
            Opcao()
        }
    }
}

@Composable
fun Opcao(){

    Column(

    ) {
        Row {
            HorizontalDivider(modifier = Modifier.width(300.dp))
        }
        Text("Jogo")
    }



    Spacer(modifier = Modifier.height(67.dp))
}