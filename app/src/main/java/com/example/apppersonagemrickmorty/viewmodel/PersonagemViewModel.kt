package com.example.apppersonagemrickmorty.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppersonagemrickmorty.database.PersonagemDatabase
import com.example.apppersonagemrickmorty.database.PersonagemEntity
import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.repository.PersonagemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface BuscaState {
    object Idle : BuscaState
    object Loading : BuscaState
    data class Success(val personagens: List<Personagem>) : BuscaState
    data class Error(val message : String) : BuscaState
}

class PersonagemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PersonagemRepository(
        dao = PersonagemDatabase.getInstance(application).personagemDao()
    )

    val personagensFavoritos : StateFlow<List<PersonagemEntity>> = repository.obterFavoritos().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    var buscaState : BuscaState by mutableStateOf(BuscaState.Idle)
        private set

    fun buscar(query: String) {
        if (query.isBlank()) {
            buscaState = BuscaState.Idle
            return
        }
        viewModelScope.launch {
            buscaState = BuscaState.Loading
            buscaState = try {
                val resultado = repository.buscarNaApi(query)
                BuscaState.Success(resultado)
            } catch (e: Exception) {
                BuscaState.Error("Não foi possível buscar agora.")
            }
        }
    }
    fun toggleFavorito(personagem: Personagem) {
        viewModelScope.launch {
            repository.toggleFavorito(personagem)
        }
    }
}