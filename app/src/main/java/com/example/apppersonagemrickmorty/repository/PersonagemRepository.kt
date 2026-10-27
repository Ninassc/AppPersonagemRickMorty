package com.example.apppersonagemrickmorty.repository

import com.example.apppersonagemrickmorty.database.PersonagemDao
import com.example.apppersonagemrickmorty.database.PersonagemEntity
import com.example.apppersonagemrickmorty.database.toEntity
import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.network.PersonagemService
import com.example.apppersonagemrickmorty.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class PersonagemRepository(
    private  val dao : PersonagemDao,
    private val api : PersonagemService = RetrofitClient.apiService
) {
    suspend fun buscarNaApi(query : String) : List<Personagem> {
        val id = query.toIntOrNull()
        return if (id != null) {
            listOf(api.getCharacterById(id))
        }
        else {
            api.searchCharactersByName(query).results
        }
    }

    suspend fun toggleFavorito(personagem: Personagem) {
        val eFavorito = dao.isFavorite(personagem.id)

        if(eFavorito){
            dao.deleteFavorite(personagem.toEntity())
        }
        else {
            dao.insertFavorite(personagem.toEntity())
        }
    }

    fun obterFavoritos() : Flow<List<PersonagemEntity>> {
        return  dao.observeFavorites()
    }
}