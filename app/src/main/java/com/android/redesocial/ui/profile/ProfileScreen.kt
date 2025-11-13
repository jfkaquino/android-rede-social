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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.redesocial.ui.BarraInferior
import com.android.redesocial.ui.BarraSuperiorMenu
import com.android.redesocial.ui.post.PostItem
import com.android.redesocial.ui.post.toFriendlyDate
import com.android.redesocial.viewmodel.AuthViewModel
import com.android.redesocial.viewmodel.PostViewModel
import com.android.redesocial.viewmodel.PostViewModelFactory

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    userId: String = authViewModel.getUidDoUsuario()!!
){
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(authViewModel)
    )

    val name: String? = authViewModel.getProfileName()
    val email: String? = authViewModel.getAccountEmail()

    val posts by viewModel.userProfilePosts.collectAsState()
    val postCount by viewModel.userPostCount.collectAsState()
    val lastPostDate by viewModel.userLastPostDate.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadProfileForUser(userId)
    }

    Scaffold(
        topBar = {
            BarraSuperiorMenu(
                title = "",
                navController = navController
            ) },
        bottomBar = { BarraInferior(
            navController
        ) }
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
                        text = name ?: "",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = email ?: "",
                        style = MaterialTheme.typography.labelLarge
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
                            text = postCount.toString(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            "Posts",
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
                            text = lastPostDate?.toFriendlyDate("dd/MM/yy") ?: "Nenhum post",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            "Último post",
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
                if (authViewModel.getUidDoUsuario() == userId) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("TelaCadastro/${userId}")
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Editar perfil",
                            )
                        }
                        Spacer(Modifier.width(20.dp))
                        Button(
                            onClick = {
                                authViewModel.signOut()
                                navController.navigate("login")
                            },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = "Sair",
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

            items(posts) { post ->
                PostItem(post = post)
            }
        }
    }

}