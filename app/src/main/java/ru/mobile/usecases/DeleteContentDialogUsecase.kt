package ru.mobile.usecases

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import ru.mobile.R
import ru.mobile.activities.LentaActivity
import ru.mobile.entities.LogEventTypeEnum
import ru.mobile.entities.dto.LentaContentDTO
import ru.mobile.entities.dto.SelectedContentDTO
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.services.ContentService
import ru.mobile.services.UserService

class DeleteContentDialogUsecase {

    private val userService: UserService = UserService()

    fun execute(contentActivity: Activity) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(contentActivity)

        val resources = contentActivity.resources!!

        builder.setTitle(resources.getString(R.string.label_delete_confirm))
        builder.setMessage(resources.getString(R.string.label_delete))

        builder.setPositiveButton(
            resources.getString(R.string.label_yes),
            DialogInterface.OnClickListener { dialog, which -> // Do nothing, but close the dialog

                if (SelectedContentDTO.publication!!.userUUID != UserEntityDTO.userUUID!!) {
                    return@OnClickListener
                }
                val contentUUID = SelectedContentDTO.publication!!.contentUUID
                userService.createLog(UserEntityDTO.accessToken!!, UserEntityDTO.userUUID.toString(), LogEventTypeEnum.DELETE_CONTENT, "{ \"contentUUID\":\"$contentUUID\" }")
                deletePublication(contentUUID)
                contentActivity.startActivity(Intent(contentActivity, LentaActivity::class.java))
                contentActivity.overridePendingTransition(0, 0)
                dialog.dismiss()
            })

        builder.setNegativeButton(
            resources.getString(R.string.label_no),
            DialogInterface.OnClickListener { dialog, which -> // Do nothing
                dialog.dismiss()
            })

        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun deletePublication(contentUUID: String) {
        if (LentaContentDTO.publications.isNotEmpty()) {
            var publicationDelete: PublicationApiResponse? = null
            for (publication in LentaContentDTO.publications) {
                if (publication.contentUUID == contentUUID) {
                    publicationDelete = publication
                }
            }
            if (publicationDelete != null) {
                LentaContentDTO.publications.remove(publicationDelete)
            }
        }
    }

}