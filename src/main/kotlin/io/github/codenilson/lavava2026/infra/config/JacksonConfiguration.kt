package io.github.codenilson.lavava2026.infra.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerKotlinModule() 
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}