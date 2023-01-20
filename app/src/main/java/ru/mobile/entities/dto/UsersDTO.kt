package ru.mobile.entities.dto

import ru.mobile.entities.response.UserApiResponse

/**
 *
 * Пользователи
 * загружаются перед загрузкой контента и обновляются по web-сокету
 *
 * @date 24.07.2022
 * @author skyhunter
 *
 */
object UsersDTO {

    var users = mutableListOf<UserApiResponse>()
    var selectedUser: UserApiResponse? = null

}