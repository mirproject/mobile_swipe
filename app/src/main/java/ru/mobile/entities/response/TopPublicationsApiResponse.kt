package ru.mobile.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TopPublicationsApiResponse (

    val publications: List<PublicationApiResponse>,
    val users: List<UserApiResponse>
)