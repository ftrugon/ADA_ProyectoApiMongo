package com.tareapi.service

import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.error.exception.NotFoundException
import com.tareapi.model.Tarea
import com.tareapi.repository.TareaRepository
import com.tareapi.repository.UsuarioRepository
import com.tareapi.util.TareaDTOParser
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

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

    fun getByID(tareaID: String): Tarea {
        val optionalTarea = tareaRepository.findTareaBy_id(tareaID)

        if (!optionalTarea.isPresent ) {
            throw NotFoundException("La tarea que se quiere completar no existe")
        }

        return optionalTarea.get()
    }

    fun getAll(): List<Tarea> {
        return tareaRepository.findAll()
    }

    fun getByUsername(username:String):List<Tarea>{
        return tareaRepository.findByUsuario(username)
    }

    fun darseDeAltaATarea(username: String, tareaID: String): Tarea {

        val optionalUsuario = usuarioRepository.findByUsername(username)

        if (!optionalUsuario.isPresent ) {
            throw NotFoundException("El usuario para dar de alta a la tarea no existe")
        }

        val tarea = getByID(tareaID)

        if (tarea.usuario != ""){
            throw BadRequestException("La tarea ya tiene un usuario asignado")
        }

        tarea.usuario = username
        tarea.fecha_inicio = Date()

        tareaRepository.save(tarea)
        return tarea

    }

    fun modTarea(tareaDTO: InsertarTareaDTO, tarea: Tarea):Tarea {
        if (tareaDTO.titulo.isBlank()){
            throw BadRequestException("El titulo de la tarea no puede estar vacio")
        }

        if (tareaDTO.texto.isBlank()){
            throw BadRequestException("El cuerpo de la tarea no puede estar vacio")
        }


        tarea.titulo = tareaDTO.titulo
        tarea.texto = tareaDTO.texto

        return tareaRepository.save(tarea)
    }

    fun completarTarea(username: String,tareaID: String):Tarea{

        val tarea = getByID(tareaID)

        if (tarea.usuario == ""){
            throw BadRequestException("La tarea no esta asignada a un usuario")
        }

        if (tarea.usuario != username){
            throw BadRequestException("No puedes completar una tarea que no esta asignada a ti")
        }

        if (tarea.estado){
            throw BadRequestException("La tarea que intentas completar ya se ha completado antes")
        }



        tarea.estado = true
        tarea.fecha_final = Date()

        return tareaRepository.save(tarea)

    }

    fun desmarcarTarea(username: String,tareaID: String): Tarea {
        val tarea = getByID(tareaID)

        if (tarea.usuario == ""){
            throw BadRequestException("La tarea no esta asignada a un usuario")
        }

        if (tarea.usuario != username){
            throw BadRequestException("No puedes desmarcar una tarea que no esta asignada a ti")
        }

        if (!tarea.estado){
            throw BadRequestException("La tarea que intentas desmarcar ya se ha desmarcado antes")
        }

        tarea.estado = false
        tarea.fecha_final = null

        return tareaRepository.save(tarea)

    }

    fun eliminarTarea(tarea: Tarea) {
        tareaRepository.delete(tarea)
    }

}