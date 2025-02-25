package com.tareapi.service

import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.error.exception.NotFoundException
import com.tareapi.model.Tarea
import com.tareapi.repository.TareaRepository
import com.tareapi.util.TareaDTOParser
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository


    fun insertarTarea(tareaDTO: InsertarTareaDTO):TareaDTO {

        if (tareaDTO.titulo.isBlank()){
            throw BadRequestException("El titulo de la tarea no puede estar vacio")
        }

        if (tareaDTO.texto.isBlank()){
            throw BadRequestException("El cuerpo de la tarea no puede estar vacio")
        }

        val tarea = TareaDTOParser.InsertarTareaDTOToTarea(tareaDTO)

        tareaRepository.insert(tarea)
        return TareaDTOParser.TareaToTareaDTO(tarea)
    }

    fun darseDeAltaATarea(username: String, tareaID: String): Tarea {

        val optionalTarea = tareaRepository.findTareaBy_id(tareaID)

        if (!optionalTarea.isPresent ) {
            throw NotFoundException("El usuario no puede estar vacio")
        }

        val tarea = optionalTarea.get()

        tarea.usuario = username
        tarea.fecha_inicio = Date()

        tareaRepository.save(tarea)
        return tarea

    }

    fun getAll(): List<Tarea> {
        return tareaRepository.findAll()
    }

    fun getByUsername(username:String):List<Tarea>{
        return tareaRepository.findByUsuario(username)
    }



}