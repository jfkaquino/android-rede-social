package com.android.redesocial.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.ui.post.Post

@Preview
@Composable
fun ProfileScreen(
    /*
    authViewModel: AuthViewModel,
    user: com.google.firebase.auth.FirebaseUser
    */
){

    Scaffold(
        topBar = { BarraSuperiorMenu("") },
        bottomBar = { BarraInferior() }
    ) { innerPadding ->

        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(120.dp),
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Perfil"
                    )
                    Text(
                        "@usuario",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        "(Nome)",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Esta é minha bio",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(Modifier.height(15.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "86",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            "Seguidores",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(horizontal = 50.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "95",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            "Seguindo",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(Modifier.height(15.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                )
                Spacer(Modifier.height(10.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Editar perfil",
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Message,
                            tint = Color.White,
                            contentDescription = "Abrir bate-papo",
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                )
                Spacer(Modifier.height(15.dp))
            }

            item{
                Post()
            }
        }
    }

}