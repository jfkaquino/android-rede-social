package com.android.redesocial.ui.settings

import android.graphics.drawable.Icon
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
        topBar = { BarraSuperiorMenu("Configurações") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Opcao(Icons.Outlined.Person, "Conta")
            Opcao(Icons.Outlined.Lock, "Privacidade")
            Opcao(Icons.Outlined.Notifications, "Notificações")
            Opcao(Icons.Outlined.BrightnessMedium, "Tema")
            Opcao(Icons.Outlined.ExitToApp, "Sair")

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.width(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Opcao(icon: ImageVector, nome: String){

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
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 47.dp)
                    .size(30.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(nome)

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Ir",
                modifier = Modifier
                    .size(30.dp)
            )
        }

    }
}