package com.example.apppersonagemrickmorty.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apppersonagemrickmorty.model.Personagem
import com.example.apppersonagemrickmorty.model.PersonagemOrigin

@Entity(tableName = "personagens")
data class PersonagemEntity (
    @PrimaryKey val id : Int = 0,
    val name : String,
    val status : String,
    val species : String,
    val gender : String,
    val image : String,
)

fun Personagem.toEntity(): PersonagemEntity {
    return PersonagemEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}