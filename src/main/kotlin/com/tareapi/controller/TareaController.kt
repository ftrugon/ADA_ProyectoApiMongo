package com.tareapi.controller

import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.error.exception.UnauthorizedException
import com.tareapi.model.Tarea
import com.tareapi.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService

    @PostMapping("/crearTarea")
    fun crearTarea(
        authentication: Authentication,
        @RequestBody tareaDTO: InsertarTareaDTO
    ): ResponseEntity<TareaDTO?> {
        return ResponseEntity( tareaService.insertarTarea(tareaDTO), HttpStatus.CREATED)
    }

    @GetMapping("/obtenerTareas")
    fun findTareasPorUsuario(
        authentication: Authentication,
    ): ResponseEntity<List<Tarea>>{

        return ResponseEntity(tareaService.getByUsername(authentication.name),HttpStatus.OK)

    }

    @GetMapping("/obtenerTareasSinAsignar")
    fun obtenerTareasSinAsignar(
        authentication: Authentication
    ): ResponseEntity<List<Tarea>>{
        return ResponseEntity(tareaService.getByUsername(""),HttpStatus.OK)
    }

    @GetMapping("/obtenerTodasTareas")
    fun findTodasTareas(
        authentication: Authentication,
    ): ResponseEntity<List<Tarea>>{

        return ResponseEntity(tareaService.getAll(),HttpStatus.OK)
    }


    @PutMapping("/asignarTarea/{tareaId}")
    fun darAltaTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ): ResponseEntity<Tarea> {
        return ResponseEntity(tareaService.darseDeAltaATarea(authentication.name,tareaId),HttpStatus.OK)
    }

    @PutMapping("/asignarTareaAUsuario/{username}/{tareaId}")
    fun darAltaTareaAdmin(
        authentication: Authentication,
        @PathVariable username: String,
        @PathVariable tareaId: String,
    ): ResponseEntity<Tarea> {
        return ResponseEntity(tareaService.darseDeAltaATarea(username,tareaId),HttpStatus.OK)
    }


    @PutMapping("/modTarea/{tareaId}")
    fun modTarea(
        authentication: Authentication,
        @PathVariable tareaId: String,
        @RequestBody tareaDTO: InsertarTareaDTO
    ): ResponseEntity<Tarea> {
        val tarea = tareaService.getByID(tareaId)

        if (authentication.authorities.any { it.authority == "ROLE_ADMIN" }){
            return ResponseEntity(tareaService.modTarea(tareaDTO,tarea),HttpStatus.OK)
        }

        if (tarea.usuario == authentication.name){
            return ResponseEntity(tareaService.modTarea(tareaDTO,tarea),HttpStatus.OK)
        }

        throw UnauthorizedException("El usuario no es administrador o no tiene la tarea asignada")

    }


    @PutMapping("/completarTarea/{tareaId}")
    fun completarTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ): ResponseEntity<Tarea>{
        return ResponseEntity( tareaService.completarTarea(authentication.name,tareaId),HttpStatus.OK)
    }


    @PutMapping("/desmarcarTarea/{tareaId}")
    fun desmarcarTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ): ResponseEntity<Tarea>{
        return ResponseEntity( tareaService.desmarcarTarea(authentication.name,tareaId),HttpStatus.OK)
    }

    @DeleteMapping("/eliminarTarea/{tareaId}")
    fun deleteTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ): ResponseEntity<Tarea>{

        val tarea = tareaService.getByID(tareaId)

        if (authentication.authorities.any { it.authority == "ROLE_ADMIN" }){
            tareaService.eliminarTarea(tarea)
            return ResponseEntity(HttpStatus.OK)
        }

        if (tarea.usuario == authentication.name){
            tareaService.eliminarTarea(tarea)
            return ResponseEntity(HttpStatus.OK)
        }

        throw UnauthorizedException("El usuario no es administrador o no tiene la tarea asignada")

    }

}