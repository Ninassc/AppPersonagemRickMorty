package com.example.apppersonagemrickmorty.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppersonagemrickmorty.database.PersonagemDatabase
import com.example.apppersonagemrickmorty.database.UsuarioEntity
import com.example.apppersonagemrickmorty.repository.UsuarioRepository
import kotlinx.coroutines.launch

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    data class Success(val usuario: UsuarioEntity) : LoginState
    data class Error(val message: String) : LoginState
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(
        dao = PersonagemDatabase.getInstance(application).usuarioDao()
    )

    var state: LoginState by mutableStateOf(LoginState.Idle)
        private set

    fun autenticar(email: String, senha: String) {
        if (email.isBlank() || senha.isBlank()) {
            state = LoginState.Error("Preencha todos os campos.")
            return
        }

        viewModelScope.launch {
            state = LoginState.Loading
            val usuario = repository.login(email.trim(), senha.trim())

            state = if (usuario != null) {
                LoginState.Success(usuario)
            } else {
                LoginState.Error("E-mail ou senha incorretos.")
            }
        }
    }
}