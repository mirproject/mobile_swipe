package ru.mobile.entities.dto

import android.graphics.Bitmap
import ru.mobile.entities.response.PublicationApiResponse

/**
 *
 * Выбранный контент
 * @date 28.06.2022
 * @author skyhunter
 *
 */
object SelectedContentDTO {

    var imageDrawable: Int = 0
    var fileName: String = ""
    var userName: String = ""
    var imageBitmapThumbnail: Bitmap? = null
    var imageBitmap: Bitmap? = null
    var publication: PublicationApiResponse? = null

}