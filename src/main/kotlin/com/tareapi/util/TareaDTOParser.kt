package com.tareapi.util

import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.model.Tarea
import java.util.Date

object TareaDTOParser {

    fun InsertarTareaDTOToTarea(tareaDTO: InsertarTareaDTO): Tarea {
        return Tarea(null,tareaDTO.titulo,tareaDTO.texto,false,Date(),"")
    }


    fun TareaToInsertarTareaDTO(tarea: Tarea): InsertarTareaDTO {
        return InsertarTareaDTO(tarea.titulo,tarea.texto)
    }

    fun TareaToTareaDTO(tarea: Tarea):TareaDTO{
        return TareaDTO(tarea.titulo,tarea.texto,tarea.estado)
    }

}