package com.tareapi.service

import com.tareapi.domain.DatosMunicipios
import com.tareapi.domain.DatosProvincias
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ExternalAPIService(private val webClient: WebClient.Builder) {
    @Value("\${API_KEY}")
    private lateinit var apiKey:String

    fun obtenerProvinciasDesdeApi(): DatosProvincias? {
        return webClient.build()
            .get()
            .uri("https://apiv1.geoapi.es/provincias?type=JSON&key=$apiKey")
            .retrieve()
            .bodyToMono(DatosProvincias::class.java)
            .block()
    }

    fun obtenerMunicipiosDesdeApi(cpro: String): DatosMunicipios? {
        return webClient.build()
            .get()
            .uri("https://apiv1.geoapi.es/municipios?CPRO=${cpro}&type=JSON&key=$apiKey")
            .retrieve()
            .bodyToMono(DatosMunicipios::class.java)
            .block()
    }

}