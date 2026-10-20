package com.example.apppersonagemrickmorty.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonagemDao {
    @Query("SELECT * FROM personagens")
    fun observeAll() : Flow<List<PersonagemEntity>>

    @Insert
    suspend fun insetAll(personagens : List<PersonagemEntity>)
}
