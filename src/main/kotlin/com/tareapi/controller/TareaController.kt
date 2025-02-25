package com.tareapi.controller

import com.tareapi.dto.InsertarTareaDTO
import com.tareapi.dto.TareaDTO
import com.tareapi.model.Tarea
import com.tareapi.model.Usuario
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
    ){

    }

    @PutMapping("/completarTarea/{tareaId}")
    fun completarTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ){

    }

    @DeleteMapping("/eliminarTarea/{tareaId}")
    fun deleteTarea(
        authentication: Authentication,
        @PathVariable tareaId: String
    ){

    }



}