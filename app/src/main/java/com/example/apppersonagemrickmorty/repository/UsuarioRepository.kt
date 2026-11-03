package com.example.apppersonagemrickmorty.repository

import com.example.apppersonagemrickmorty.database.UsuarioDao
import com.example.apppersonagemrickmorty.database.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun cadastrar(usuario: UsuarioEntity): Boolean {
        val existente = dao.buscarPorEmail(usuario.email)
        if (existente != null) return false

        dao.cadastrar(usuario)
        return true
    }

    suspend fun login(email: String, senha: String): UsuarioEntity? {
        return dao.fazerLogin(email, senha)
    }
}