package com.tareapi.repository

import com.tareapi.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByUsuario(usuario:String):List<Tarea>

    fun findTareaBy_id(_id:String): Optional<Tarea>

}