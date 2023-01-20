package ru.mobile.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.mobile.databinding.ActivityContentBinding
import ru.mobile.entities.LogEventTypeEnum
import ru.mobile.entities.dto.SelectedContentDTO
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.fragments.BaseMenuFragment
import ru.mobile.services.ContentService
import ru.mobile.services.UserService
import ru.mobile.usecases.DeleteContentDialogUsecase

class ContentActivity : AppCompatActivity() {

    private lateinit var activityContentBinding: ActivityContentBinding
    private val userService: UserService = UserService()

    private val deleteContentDialog: DeleteContentDialogUsecase = DeleteContentDialogUsecase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContentBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(activityContentBinding.root)

        BaseMenuFragment.newInstance("content")
        //UserChatsDTO.selectedActivity = this
        userService.createLog(
            UserEntityDTO.accessToken!!,
            UserEntityDTO.userUUID.toString(),
            LogEventTypeEnum.GO_PAGE,
            "{ \"pageName\":\"Контент\" }"
        )
        val selectedPublication = SelectedContentDTO.publication!!

        activityContentBinding.contentChatLayout.visibility = View.INVISIBLE
        activityContentBinding.contentSendToLayout.visibility = View.INVISIBLE
        activityContentBinding.playStartImg.visibility = View.INVISIBLE
        activityContentBinding.simpleVideoView.visibility = View.INVISIBLE
        activityContentBinding.contentLinearLayout.visibility = View.VISIBLE

        activityContentBinding.contentDelete.setOnClickListener(View.OnClickListener {
            if (selectedPublication.userUUID == UserEntityDTO.userUUID) {
                deleteContentDialog.execute(this)
            }
        })
        activityContentBinding.contentUserName.text = selectedPublication.userName

        val publication = SelectedContentDTO.publication!!
        SelectedContentDTO.imageBitmap = publication.imageBitmapThumbnail

        if (SelectedContentDTO.imageBitmap != null) {
            activityContentBinding.contentImage.setImageBitmap(SelectedContentDTO.imageBitmap)
            loadImage(publication)
        } else {
            activityContentBinding.contentImage.setImageResource(SelectedContentDTO.imageDrawable)
        }

    }

    private fun loadImage(publication: PublicationApiResponse) {
        Thread {
            runOnUiThread {
                activityContentBinding.contentImage.setImageBitmap(publication.imageBitmapBase)
                activityContentBinding.contentImage.refreshDrawableState()
            }
        }.start()
    }

}