package com.example.apppersonagemrickmorty.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apppersonagemrickmorty.model.PersonagemOrigin

@Entity(tableName = "personagens")
data class PersonagemEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name : String,
    val status : String,
    val species : String,
    val gender : String,
    val image : String,
)