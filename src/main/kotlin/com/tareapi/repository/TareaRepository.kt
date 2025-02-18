package com.tareapi.repository

import com.tareapi.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {
}