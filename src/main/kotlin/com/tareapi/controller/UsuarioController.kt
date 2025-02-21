package com.tareapi.controller

import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.UsuarioDTO
import com.tareapi.dto.UsuarioLoginDTO
import com.tareapi.error.exception.AlreadyExistException
import com.tareapi.error.exception.UnauthorizedException
import com.tareapi.service.TokenService
import com.tareapi.model.Usuario
import com.tareapi.service.UsuarioService
import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsuarioController {


    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var usuarioService: UsuarioService


    @PostMapping("/register")
    fun insert(
        httpRequest: HttpServletRequest,
        @RequestBody registrarUsuario: RegistrarUsuarioDTO,
    ): ResponseEntity<UsuarioDTO?>?{

        return ResponseEntity(usuarioService.insertUser(registrarUsuario),HttpStatus.CREATED)

    }

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginDTO: UsuarioLoginDTO) : ResponseEntity<Any> {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuarioLoginDTO.username, usuarioLoginDTO.password))
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas")
        }

        // SI PASAMOS LA AUTENTICACIÓN, SIGNIFICA QUE ESTAMOS BIEN AUTENTICADOS
        // PASAMOS A GENERAR EL TOKEN
        val token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.OK)
    }
}