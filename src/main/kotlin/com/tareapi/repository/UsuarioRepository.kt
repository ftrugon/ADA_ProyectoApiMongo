package com.tareapi.repository

import com.tareapi.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {
    fun findByUsername(username: String): Optional<Usuario>
}