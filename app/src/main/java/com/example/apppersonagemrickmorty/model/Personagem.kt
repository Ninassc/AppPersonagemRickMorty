package com.example.apppersonagemrickmorty.model

data class Personagem (
    val id : Int,
    val name : String,
    val status : String,
    val species : String,
    val gender : String,
    val image : String,
    val origin : PersonagemOrigin
)

data class PersonagemOrigin(
    val name : String
)


