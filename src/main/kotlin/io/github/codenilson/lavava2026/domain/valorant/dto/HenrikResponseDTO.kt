package io.github.codenilson.lavava2026.domain.valorant.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HenrikResponseDTO<T>(
    val status: Int,
    val data: T
)