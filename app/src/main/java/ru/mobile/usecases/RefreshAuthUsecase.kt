package ru.mobile.usecases

import android.util.Log
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.services.UserService

/**
 * Обновление авторизации через токен
 */
class RefreshAuthUsecase {

    private val userService: UserService = UserService()

    @Synchronized
    fun execute(identityToken: String) {

        val authResponse = try {
            userService.refreshAuthUser(identityToken)
        } catch (e: java.lang.Exception) {
            //AlertDialogUtil.showErrorMessage(activity, e.message)
            Log.e("LOG", e.message, e)
            return
        }

        val userResponse = try {
            userService.getUser(
                userUUID = authResponse.userUUID,
                token = authResponse.accessToken
            )
        } catch (e: java.lang.Exception) {
            //AlertDialogUtil.showErrorMessage(activity, e.message)
            Log.e("LOG", e.message, e)
            return
        }
        try {
            UserEntityDTO.setUserEntity(authResponse, userResponse)
        } catch (e: java.lang.Exception) {
            //AlertDialogUtil.showErrorMessage(activity, e.message)
            Log.e("LOG", e.message, e)
            return
        }

    }


}