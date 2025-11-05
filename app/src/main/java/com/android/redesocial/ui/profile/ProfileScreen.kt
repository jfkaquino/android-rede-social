package com.android.redesocial.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.redesocial.BarraInferior
import com.android.redesocial.BarraSuperiorMenu
import com.android.redesocial.BarraSuperior
import com.android.redesocial.ui.home.Post
import com.android.redesocial.viewmodel.AuthViewModel

@Preview
@Composable
fun Profile(
    /*
    authViewModel: AuthViewModel,
    user: com.google.firebase.auth.FirebaseUser
    */
){

    Scaffold(
        //topBar = { BarraSuperiorMenu("") },
        bottomBar = { BarraInferior() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(105.dp)
                ) {
                    drawRect(Color(0xFF2CC4B0))
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 8.dp, top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Configurações",
                        tint = Color.White
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.BottomCenter)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Spacer(Modifier.height(15.dp))

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
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Editar perfil",
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddCircleOutline,
                            tint = Color.White,
                            contentDescription = "Abrir bate-papo",
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Editar perfil",
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
        }
    }

}