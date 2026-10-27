package com.example.apppersonagemrickmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonagemEntity::class], version = 1, exportSchema = false)
abstract  class PersonagemDatabase : RoomDatabase() {
    abstract  fun personagemDao() : PersonagemDao

    companion object {
        @Volatile private  var INSTANCE : PersonagemDatabase? = null

        fun getInstance(context: Context) : PersonagemDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PersonagemDatabase::class.java,
                    "personagem.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}