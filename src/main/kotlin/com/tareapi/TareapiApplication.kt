package com.tareapi

import com.tareapi.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class TareapiApplication

fun main(args: Array<String>) {
	runApplication<TareapiApplication>(*args)
}
