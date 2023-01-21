package ru.mobile.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.mobile.databinding.ActivityContentBinding
import ru.mobile.entities.LogEventTypeEnum
import ru.mobile.entities.dto.LentaContentDTO
import ru.mobile.entities.dto.SelectedContentDTO
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.fragments.BaseMenuFragment
import ru.mobile.services.UserService
import ru.mobile.usecases.DeleteContentDialogUsecase
import ru.mobile.utils.OnSwipeTouchListener

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
        swipeListener()
        createScreen()
    }

    private fun createScreen() {
        activityContentBinding.contentChatLayout.visibility = View.INVISIBLE
        activityContentBinding.contentSendToLayout.visibility = View.INVISIBLE
        activityContentBinding.playStartImg.visibility = View.INVISIBLE
        activityContentBinding.simpleVideoView.visibility = View.INVISIBLE
        activityContentBinding.contentLinearLayout.visibility = View.VISIBLE
        val publication = SelectedContentDTO.publication!!
        activityContentBinding.contentUserName.text = publication.userName

        SelectedContentDTO.imageBitmap = publication.imageBitmapThumbnail
        if (publication.imageBitmapBase != null) {
            activityContentBinding.contentImage.setImageBitmap(publication.imageBitmapBase)
            loadImage(publication)
        }
        onClickHandler(publication)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun swipeListener() {
        activityContentBinding.contentImage.setOnTouchListener(object :
            OnSwipeTouchListener(this@ContentActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                changeContent("next")
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                changeContent("previous")
            }
        })
    }

    private fun changeContent(swipe: String) {
        val selectedPublicationIndex = SelectedContentDTO.publication?.let {
            LentaContentDTO.publications.indexOf(it)
        } ?: 0
        val previousPublicationIndex =
            if (selectedPublicationIndex == 0) 0 else selectedPublicationIndex - 1
        val nextPublicationIndex = selectedPublicationIndex + 1
        val publication = when (swipe) {
            "next" -> if (nextPublicationIndex < LentaContentDTO.publications.size) LentaContentDTO.publications[nextPublicationIndex] else SelectedContentDTO.publication
            "previous" -> if (previousPublicationIndex < LentaContentDTO.publications.size) LentaContentDTO.publications[previousPublicationIndex] else SelectedContentDTO.publication
            else -> SelectedContentDTO.publication
        }
        SelectedContentDTO.publication = publication
        SelectedContentDTO.imageBitmap = publication?.imageBitmapBase
        SelectedContentDTO.fileName = publication?.description ?: ""
        SelectedContentDTO.userName = publication?.userName ?: ""
        SelectedContentDTO.imageBitmapThumbnail = publication?.imageBitmapThumbnail
        createScreen()
    }

    private fun onClickHandler(selectedPublication: PublicationApiResponse) =
        activityContentBinding.contentDelete.setOnClickListener {
            if (selectedPublication.userUUID == UserEntityDTO.userUUID)
                deleteContentDialog.execute(this)
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