package com.example.apppersonagemrickmorty.network

import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.model.PersonagemOrigin
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class PersonagemResponse(
    val results: List<Personagem>
)

interface PersonagemService {

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Personagem

    @GET("character")
    suspend fun searchCharactersByName(@Query("name") name: String): PersonagemResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val apiService: PersonagemService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonagemService::class.java)
    }
}

