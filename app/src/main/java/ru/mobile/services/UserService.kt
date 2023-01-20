package ru.mobile.services

import android.util.Log
import ru.mobile.entities.LogEventTypeEnum
import ru.mobile.entities.response.AuthUserApiResponse
import ru.mobile.entities.response.UserApiResponse

class UserService {

    fun refreshAuthUser(
        refreshToken: String
    ): AuthUserApiResponse {
        return AuthUserApiResponse(
            accessToken = "test",
            refreshToken = "test",
            tokenType = "test",
            expiresSeconds = 999999,
            userUUID = "test"
        )
    }

    fun getUser(
        userUUID: String,
        token: String
    ): UserApiResponse {
        return UserApiResponse(
            userUUID = "test",
            userName= "test",
            userPhone= "test",
            email= "test",
            fullName= "test",
            groupName= "test",
            userBlocked = false,
            commentOnChange = "test",
            fileUUID = "test",
            userPayload = "test",
            imageBitmap = null,

            userSubscribers =  mutableListOf(),
            userLikes =  mutableListOf(),
            userFavorites =  mutableListOf(),
            //userAlbums =  mutableListOf()
        )
    }

    fun createLog(
        token: String,
        userUUID: String,
        logEventType: LogEventTypeEnum,
        payload: String
    ) {
        Log.e("LOG", payload)
    }

}