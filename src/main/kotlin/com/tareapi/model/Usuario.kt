package com.tareapi.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("coll_Usuarios")
data class Usuario(
    @BsonId
    val _id : String?,
    @Indexed(unique = true)
    val username: String,
    var password: String,
    @Indexed(unique = true)
    val email: String,
    val direccion: Direccion,
    val roles: String? = "USER"
) {

}
