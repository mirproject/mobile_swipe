package ru.mobile.usecases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import ru.mobile.R
import ru.mobile.activities.ContentActivity
import ru.mobile.activities.LentaActivity
import ru.mobile.config.ConfigApp
import ru.mobile.entities.ContentTypeEnum
import ru.mobile.entities.dto.LentaContentDTO
import ru.mobile.entities.dto.SelectedContentDTO
import ru.mobile.entities.dto.UsersDTO
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.services.ContentService
import ru.mobile.utils.ImageUtil
import ru.mobile.utils.resize

class AddContentToTableUsecase {

    private val contentService: ContentService = ContentService()

    @Synchronized
    fun execute(activity: Activity, tableLayout: TableLayout) {

        if (LentaContentDTO.publications.isEmpty()) {
            return
        }
        tableLayout.removeAllViews()
        var imageRowCount = 0
        var tableRow = TableRow(activity)

        for (publication in LentaContentDTO.publications) {

            if (publication.fileUUID.isNullOrEmpty() || publication.imageBitmapThumbnail == null) {
                continue
            }
            if (imageRowCount == 0) {
                tableRow = ImageUtil.createNewTableRow(activity, 2F)
            }
           // publication.imageBitmapUser = getUserImage(publication.userUUID)
            addImageToTableRow(
                tableRow = tableRow,
                imageBitmapThumbnail = publication.imageBitmapThumbnail!!,
                imageUserBitmap = publication.imageBitmapUser,
                imageName = publication.description!!,
                imageId = publication.hashCode(),
                context = activity,
                publication = publication
            )
            imageRowCount += 1
            if (imageRowCount == 2) {
                imageRowCount = 0
                tableLayout.addView(
                    tableRow,
                    TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
        }

        if (imageRowCount == 1) {
            tableLayout.addView(
                tableRow,
                TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    private fun addImageToTableRow(
        tableRow: TableRow,
        imageBitmapThumbnail: Bitmap,
        imageUserBitmap: Bitmap?,
        imageName: String,
        imageId: Int,
        context: Context,
        publication: PublicationApiResponse
    ) {

        val scrollWidth = ImageUtil.getScreenWidth() / 2
        val imageBitmap = imageBitmapThumbnail.resize(scrollWidth, scrollWidth * 2)

        val contentImageBtn = ImageUtil.createRoundImageView(
            imageBitmap = BitmapDrawable(context.resources, imageBitmap),
            imageName = imageName,
            imageId = imageId,
            context = context,
            imageHeight = 350,
            radius = context.resources.getDimension(R.dimen.image_round)
        )

        //Нажатие на картинку контента
        contentImageBtn.setOnClickListener(
            View.OnClickListener {
                SelectedContentDTO.fileName = imageName
                SelectedContentDTO.publication = publication
                SelectedContentDTO.imageBitmapThumbnail = imageBitmap
                //В ленте или в карте
                if (context is LentaActivity) {
                    //Видео или картинка
                    if (ContentTypeEnum.VIDEO.name == publication.contentType) {
                        //context.startActivity(Intent(context, ContentVideoActivity::class.java))
                    } else {
                        context.startActivity(Intent(context, ContentActivity::class.java))
                    }
                }
                //if (context is Activity) {
                //    context.overridePendingTransition(0, 0)
                //}
            }
        )
        //Иконка пользователя
        val imageIcon = ImageUtil.createImageIcon(
            context = context,
            drawable = context.resources.getDrawable(R.drawable.empty_profile, null),
            backgroundDrawable = context.resources.getDrawable(R.drawable.button_round_while, null),
            imageUserBitmap = imageUserBitmap
        )

        //Имя пользователя
        val nameIcon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ImageUtil.createTextIconName(
                context = context,
                iconName = publication.userName,
                shadowColor = context.resources.getColor(R.color.black, null),
                textColor = context.resources.getColor(R.color.white, null),
                marginStart = 55
            )
        } else {
            ImageUtil.createTextIconName(
                context = context,
                iconName = publication.userName,
                shadowColor = ContextCompat.getColor(context, R.color.black),
                textColor = ContextCompat.getColor(context, R.color.white),
                marginStart = 55
            )
        }

        val frameLayout = ImageUtil.createNewFrameLayout(context, 0, 350)
        frameLayout.id = imageId + 1000
        frameLayout.addView(contentImageBtn, scrollWidth - 10, frameLayout.layoutParams.height)

        //Кнопка просмотра видео
        if (ContentTypeEnum.VIDEO.name == publication.contentType) {
            val imageType = ImageUtil.createImageTypeIcon(
                context,
                context.resources.getDrawable(R.drawable.baseline_play_arrow_24, null),
                context.resources.getDrawable(R.drawable.button_round_while_trans, null)
            )
            frameLayout.addView(imageType)
        }
        frameLayout.addView(imageIcon)
        frameLayout.addView(nameIcon)
        tableRow.addView(frameLayout)
    }


}