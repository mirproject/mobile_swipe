package ru.mobile.entities.dto

import android.graphics.Bitmap
import ru.mobile.entities.response.AuthUserApiResponse
import ru.mobile.entities.response.UserApiResponse

/**
 *
 * Данные пользователя
 * @date 22.06.2022
 * @author skyhunter
 *
 */
object UserEntityDTO {

    var accessToken: String? = null
    var refreshToken: String? = null
    var tokenType: String? = null
    var expiresSeconds: Int? = null
    var userUUID: String? = null

    var userName: String? = null
    var userPhone: String? = null
    var userEmail: String? = null
    var fullName: String? = null
    var groupName: String? = null
    var password: String? = null

    var userRegistration: UserApiResponse? = null

    var fileUUID: String? = null
    var userImage: Bitmap? = null

    var userLikes: MutableList<String> = emptyList<String>().toMutableList()
    var userFavorites: MutableList<String> = emptyList<String>().toMutableList()
    var userPayload: String? = null

    fun setUserEntity(authResponse: AuthUserApiResponse, userResponse: UserApiResponse) {
        userUUID = authResponse.userUUID
        refreshToken = authResponse.refreshToken
        accessToken = authResponse.accessToken
        expiresSeconds = authResponse.expiresSeconds
        tokenType = authResponse.tokenType

        userName = userResponse.userName
        userPhone = userResponse.userPhone
        userName = userResponse.userName
        userEmail = userResponse.email
        fullName = userResponse.fullName
        groupName = userResponse.groupName
        fileUUID = userResponse.fileUUID

        userLikes = userResponse.userLikes
        userFavorites = userResponse.userFavorites

        userPayload = userResponse.userPayload
    }

    fun setUserResponse(userResponse: UserApiResponse) {
        userName = userResponse.userName
        userPhone = userResponse.userPhone
        userName = userResponse.userName
        userEmail = userResponse.email
        fullName = userResponse.fullName
        groupName = userResponse.groupName
        fileUUID = userResponse.fileUUID

        userLikes = userResponse.userLikes
        userFavorites = userResponse.userFavorites

        userPayload = userResponse.userPayload
    }
}