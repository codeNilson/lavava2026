package io.github.codenilson.lavava2026.infra.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ValorantApiClient(
    @param:Value("\${api.valorant.base_url}") private val baseUrl: String,
    @param:Value("\${api.valorant.key}") private val apiKey: String
) {
    @Bean
    fun valorantWebClient(builder: WebClient.Builder): WebClient {
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) // 2MB
            }
            .build()

        return builder
            .baseUrl(baseUrl)
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", apiKey)
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}
