package ru.mobile.services

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.entities.response.TopPublicationsApiResponse
import ru.mobile.entities.response.UserApiResponse
import ru.mobile.R
import ru.mobile.entities.ContentTypeEnum

class ContentService {

    private val userService: UserService = UserService()

    @Synchronized
    fun getTopContent(
        token: String,
        countTop: Int,
        context: Context
    ): TopPublicationsApiResponse {

        val publications: MutableList<PublicationApiResponse> = mutableListOf()
        publications.add(generatePublication("test1", R.drawable.post1, context.resources))
        publications.add(generatePublication("test2", R.drawable.post2, context.resources))
        publications.add(generatePublication("test3", R.drawable.post3, context.resources))
        publications.add(generatePublication("test4", R.drawable.post4, context.resources))

        val users = emptyList<UserApiResponse>().toMutableList()
        val user = userService.getUser(
            userUUID = "test",
            token = token
        )
        users.add(user)
        return TopPublicationsApiResponse(
            publications = publications,
            users = users
        )
    }

    private fun generatePublication(name: String, post: Int, resources: Resources): PublicationApiResponse {
        return PublicationApiResponse(
            userUUID = "test",
            userName= "test",
            contentUUID= name,
            contentType= ContentTypeEnum.IMAGE.name,
            description= name,
            coordinates = name,
            categories = name,
            fileUUID = name,
            thumbnailFileUUID = name,
            created = name,
            updated = name,
            imageBitmapThumbnail =  BitmapFactory.decodeResource(resources, post),
            imageBitmapBase =  BitmapFactory.decodeResource(resources, post),
            imageBitmapUser =  BitmapFactory.decodeResource(resources, R.drawable.profile_photo),
        )
    }


}