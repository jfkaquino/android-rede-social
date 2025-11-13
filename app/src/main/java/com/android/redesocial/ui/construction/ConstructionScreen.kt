package com.android.redesocial.ui.construction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.redesocial.ui.BarraSuperiorOpcao
import com.android.redesocial.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstructionScreen(navController: NavController? = null) {
    Scaffold(
        topBar = { BarraSuperiorOpcao(navController, "Em construção") }
    )  { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.obras),
                contentDescription = "Tela em construção",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "Essa tela ainda não está pronta!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConstructionScreen() {
    ConstructionScreen()
}