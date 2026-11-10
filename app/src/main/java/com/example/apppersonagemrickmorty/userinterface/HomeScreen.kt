package com.example.apppersonagemrickmorty.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.userinterface.components.PersonagemCard
import com.example.apppersonagemrickmorty.viewmodel.BuscaState
import com.example.apppersonagemrickmorty.viewmodel.PersonagemViewModel

@Composable
fun HomeScreen(
    viewModel: PersonagemViewModel,
    onPersonagemClick: (Int) -> Unit,
    onIrParaFavoritos: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val favoritos by viewModel.personagensFavoritos.collectAsState()
    val favoritosIds = remember(favoritos) { favoritos.map { it.id }.toSet() }

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
                    // Barra de pesquisa em pílula
                    OutlinedTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            viewModel.buscar(it)
                        },
                        placeholder = { Text("Buscar por nome ou ID...") },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color(0xFF1E1E1E)
                            )
                        },
                        shape = RoundedCornerShape(50),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFCCDCE3),
                            unfocusedContainerColor = Color(0xFFCCDCE3),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Atalho para lista de favoritos
                    IconButton(onClick = onIrParaFavoritos) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.Red
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Área do conteúdo central do container
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCCDCE3))
                ) {
                    when (val state = viewModel.buscaState) {
                        is BuscaState.Idle -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Digite o nome ou ID do personagem para buscar.",
                                    color = Color(0xFF5A758D),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        is BuscaState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0xFF5A758D))
                            }
                        }

                        is BuscaState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.message,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        is BuscaState.Success -> {
                            val lista = state.personagens
                            Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                                Text(
                                    text = "${lista.size} Personagens Relacionados",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E1E1E),
                                    modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    contentPadding = PaddingValues(4.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(lista) { personagem ->
                                        PersonagemCard(
                                            nome = personagem.name,
                                            imagemUrl = personagem.image,
                                            isFavorite = favoritosIds.contains(personagem.id),
                                            onCardClick = { onPersonagemClick(personagem.id) },
                                            onFavoriteToggle = { viewModel.toggleFavorito(personagem) },
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}