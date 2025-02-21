package com.tareapi.util

import com.tareapi.dto.RegistrarUsuarioDTO
import com.tareapi.dto.UsuarioDTO
import com.tareapi.model.Usuario

object UsuarioDTOParser {

    fun registrarDTOToUsuario(registrarUsuarioDTO: RegistrarUsuarioDTO):Usuario{
        return Usuario(
            null,
            registrarUsuarioDTO.username,
            registrarUsuarioDTO.password,
            registrarUsuarioDTO.email,
            registrarUsuarioDTO.direccion,
            registrarUsuarioDTO.rol
        )
    }

    fun usuarioToDto(usuario: Usuario):UsuarioDTO{
        return UsuarioDTO(
            usuario.username,
            usuario.email
        )
    }

}