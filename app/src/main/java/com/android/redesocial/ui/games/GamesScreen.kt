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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.R

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

            //Opcao("Sudoku", R.drawable.sudoku)
            //Opcao("Paciência", R.drawable.paciencia3)
            //Opcao("Jogo da Velha", R.drawable.velha)
            //Opcao("Xadrez", R.drawable.xadrez)

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.width(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Opcao(nome: String, @DrawableRes imageResId: Int){

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(thickness = 1.dp,
            modifier = Modifier.width(300.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Nome",
                modifier = Modifier
                    .height(65.dp)
                    .padding(start = 47.dp),
                contentScale = ContentScale.Fit
            )
            Text(nome)
        }

    }
}