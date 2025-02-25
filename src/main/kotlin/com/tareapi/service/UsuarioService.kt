package com.tareapi.service

import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.UsuarioDTO
import com.tareapi.error.exception.AlreadyExistException
import com.tareapi.error.exception.UnauthorizedException
import com.tareapi.model.Direccion
import com.tareapi.model.Usuario
import com.tareapi.repository.UsuarioRepository
import com.tareapi.util.UsuarioDTOParser
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UsuarioService: UserDetailsService {

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var externalAPIService: ExternalAPIService

    override fun loadUserByUsername(username: String?): UserDetails {

        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow {
                UnauthorizedException("$username no existente")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun insertUser(registrarUsuarioDTO: RegistrarUsuarioDTO):UsuarioDTO{

        // COMPROBACIONES
        // campos
        if (registrarUsuarioDTO.username.isBlank()
            || registrarUsuarioDTO.email.isBlank()
            || registrarUsuarioDTO.password.isBlank()) {
            throw BadRequestException("Uno o más campos vacíos")
        }

        if (registrarUsuarioDTO.password.length < 6) {
            throw BadRequestException("La contraseña no puede ser menor de 6 caracteres")
        }

        if (registrarUsuarioDTO.password != registrarUsuarioDTO.passwordRepeat) {
            throw BadRequestException("La contraseña no coincide")
        }

        // existe
        if(usuarioRepository.findByUsername(registrarUsuarioDTO.username).isPresent) {
            throw AlreadyExistException("Usuario ${registrarUsuarioDTO.username} ya está registrado")
        }

        // rol user o admin
        if(registrarUsuarioDTO.rol != null && registrarUsuarioDTO.rol != "USER" && registrarUsuarioDTO.rol != "ADMIN" ) {
            throw BadRequestException("ROL: ${registrarUsuarioDTO.rol} incorrecto")
        }

        // direccion
        comprobarDireccion(registrarUsuarioDTO.direccion)


        // meter al usuario en la bd
        val usuario = UsuarioDTOParser.registrarDTOToUsuario(registrarUsuarioDTO)
        usuario.password = passwordEncoder.encode(registrarUsuarioDTO.password)
        usuarioRepository.save(usuario)

        return UsuarioDTOParser.usuarioToDto(usuario)
    }

    private fun comprobarDireccion(direccion: Direccion) {
        // Comprobar la provincia
        val datosProvincias = externalAPIService.obtenerProvinciasDesdeApi()
        var cpro: String = ""
        if(datosProvincias != null) {
            if(datosProvincias.data != null) {
                val provinciaEncontrada = datosProvincias.data.stream().filter {
                    it.PRO == direccion.provincia.uppercase()
                }.findFirst().orElseThrow {
                    BadRequestException("Provincia ${direccion.provincia} no encontrada")
                }
                cpro = provinciaEncontrada.CPRO
            }
        }

        // Comprobar el municipio
        val datosMunicipios = externalAPIService.obtenerMunicipiosDesdeApi(cpro)
        if(datosMunicipios != null) {
            if(datosMunicipios.data != null) {
                datosMunicipios.data.stream().filter {
                    it.DMUN50 == direccion.municipio.uppercase()
                }.findFirst().orElseThrow {
                    BadRequestException("Municipio ${direccion.municipio} incorrecto")
                }
            }
        }
    }

}