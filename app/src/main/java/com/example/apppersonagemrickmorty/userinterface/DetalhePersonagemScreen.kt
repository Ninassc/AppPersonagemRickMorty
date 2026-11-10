package com.example.apppersonagemrickmorty.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.network.RetrofitClient
import com.example.apppersonagemrickmorty.viewmodel.PersonagemViewModel

@Composable
fun DetalhePersonagemScreen(
    personagemId: Int,
    viewModel: PersonagemViewModel,
    onVoltar: () -> Unit
) {
    var personagem by remember { mutableStateOf<Personagem?>(null) }
    var estaCarregando by remember { mutableStateOf(true) }

    val favoritos by viewModel.personagensFavoritos.collectAsState()
    val isFavorite = remember(favoritos, personagemId) {
        favoritos.any { it.id == personagemId }
    }

    LaunchedEffect(personagemId) {
        try {
            personagem = RetrofitClient.apiService.getCharacterById(personagemId)
        } catch (_: Exception) {
        } finally {
            estaCarregando = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE2ECF0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color(0xFF1E1E1E)
                        )
                    }

                    Text(
                        text = "Detalhes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E),
                        modifier = Modifier.weight(1f)
                    )

                    personagem?.let { p ->
                        IconButton(onClick = { viewModel.toggleFavorito(p) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favoritar",
                                tint = if (isFavorite) Color.Red else Color(0xFF1E1E1E)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCCDCE3))
                ) {
                    if (estaCarregando) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF5A758D))
                        }
                    } else if (personagem != null) {
                        val item = personagem!!
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = item.image,
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp))
                            )

                            Spacer(Modifier.height(20.dp))

                            Text(
                                text = item.name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E1E1E)
                            )

                            Spacer(Modifier.height(16.dp))

                            ItemDetalhe(label = "Status", valor = item.status)
                            ItemDetalhe(label = "Espécie", valor = item.species)
                            ItemDetalhe(label = "Gênero", valor = item.gender)
                            if (item.origin.name.isNotBlank()) {
                                ItemDetalhe(label = "Origem", valor = item.origin.name)
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Erro ao carregar detalhes.", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemDetalhe(label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5A758D),
            fontSize = 16.sp
        )
        Text(
            text = valor,
            color = Color(0xFF1E1E1E),
            fontSize = 16.sp
        )
    }
}