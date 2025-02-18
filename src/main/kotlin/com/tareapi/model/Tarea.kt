package com.tareapi.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("coll_Tareas")
class Tarea (
    @BsonId
    val _id : String?,
    val titulo: String,
    val texto: String,
    val estado: Boolean,
    val fecha_inicio: Date,
    val usuario: Usuario
){

}