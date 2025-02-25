package com.tareapi.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("coll_Tareas")
class Tarea (
    @BsonId
    val _id : String?,
    var titulo: String,
    var texto: String,
    var estado: Boolean,
    var fecha_inicio: Date,
    var usuario: String?
){
}