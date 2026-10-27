package com.example.apppersonagemrickmorty.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonagemDao {
    @Query("SELECT * FROM personagens")
    fun observeFavorites() : Flow<List<PersonagemEntity>>

    @Insert
    suspend fun insertFavorite(personagens : PersonagemEntity)

    @Delete
    suspend fun deleteFavorite(personagem: PersonagemEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM personagens WHERE id = :id)")
    suspend fun isFavorite(id: Int) : Boolean
}
