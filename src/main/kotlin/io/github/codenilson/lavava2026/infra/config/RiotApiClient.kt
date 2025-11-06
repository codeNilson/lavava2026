package io.github.codenilson.lavava2026.infra.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class RiotApiClient(@param:Value("\${api.base_url}") private val baseUrl: String) {
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(baseUrl)
            .defaultHeader("Content-Type", "application/json")
//            .defaultHeader("Authorization", "")
            .build()
    }
}
