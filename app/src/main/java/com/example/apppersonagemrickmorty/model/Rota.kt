package com.example.apppersonagemrickmorty.model

import kotlinx.serialization.Serializable

sealed interface Rota {
    @Serializable
    data object Splash : Rota

    @Serializable
    data object Cadastro : Rota

    @Serializable
    data object Login : Rota

    @Serializable
    data object Home : Rota

    @Serializable
    data object ListaFavoritos : Rota

    @Serializable
    data class  DetalhePersonagem(val personagemId : Int) : Rota
}