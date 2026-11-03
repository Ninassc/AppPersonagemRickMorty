package com.example.apppersonagemrickmorty.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun cadastrar(usuario: UsuarioEntity)

    // Retorna o usuário se o e-mail e senha conferirem; caso contrário, retorna null
    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun fazerLogin(email: String, senha: String): UsuarioEntity?

    // Para evitar cadastrar dois usuários com o mesmo e-mail
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): UsuarioEntity?
}