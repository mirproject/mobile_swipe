package ru.mobile.entities.response

import android.graphics.Bitmap
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * ������ ������������
 * @date 23.06.2022
 * @author skyhunter
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserApiResponse(

    @JsonProperty(value = "uuid")
    val userUUID: String,
    val userName: String,
    val userPhone: String?,
    val email: String?,
    val fullName: String?,
    val groupName: String,
    val userBlocked: Boolean,
    val commentOnChange: String?,
    @JsonProperty(value = "userFileUuid")
    val fileUUID: String?,
    val userPayload: String?,

    var imageBitmap: Bitmap?,

    val userSubscribers: MutableList<String>,
    val userLikes: MutableList<String>,
    val userFavorites: MutableList<String>,
    //val userAlbums: MutableList<AlbumApiResponse>
)
