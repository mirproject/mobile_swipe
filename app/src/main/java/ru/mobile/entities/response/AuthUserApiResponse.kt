package ru.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthUserApiResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresSeconds: Int,
    val userUUID: String
)
