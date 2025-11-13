package com.android.redesocial.ui.games

import androidx.annotation.DrawableRes
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
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.redesocial.ui.BarraSuperiorOpcao
import com.android.redesocial.R
import com.android.redesocial.ui.BarraInferior

@Composable
fun GamesScreen(navController: NavController){

    Scaffold(
        topBar = { BarraSuperiorOpcao(navController, "Jogos") },
        bottomBar = { BarraInferior(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Opcao("Sudoku", R.drawable.sudoku) { navController.navigate("construction") }
            Opcao("Paciência", R.drawable.paciencia3) { navController.navigate("construction") }
            Opcao("Jogo da Velha", R.drawable.velha) { navController.navigate("construction") }
            Opcao("Xadrez", R.drawable.xadrez) { navController.navigate("xadrez") }

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.width(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Opcao(nome: String, @DrawableRes imageResId: Int, onClick: () -> Unit = {}){

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 47.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(nome)

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .size(30.dp)
            ){Icon(
                imageVector = Icons.Outlined.ArrowCircleRight,
                contentDescription = "Ir",
                modifier = Modifier.size(28.dp)
            )}
        }
    }
}