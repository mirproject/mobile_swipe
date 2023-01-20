package ru.mobile.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.mobile.databinding.ActivityLentaBinding
import ru.mobile.entities.dto.LentaContentDTO
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.entities.dto.UsersDTO
import ru.mobile.entities.response.PublicationApiResponse
import ru.mobile.fragments.BaseMenuFragment
import ru.mobile.services.ContentService
import ru.mobile.usecases.UploadLentaUsecase

class LentaActivity : AppCompatActivity() {

    private lateinit var activityLentaBinding: ActivityLentaBinding
    //private lateinit var progress: AlertDialog

    private val contentService: ContentService = ContentService()
    private val uploadLentaUsecase: UploadLentaUsecase = UploadLentaUsecase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLentaBinding = ActivityLentaBinding.inflate(layoutInflater)
        setContentView(activityLentaBinding.root)
        //progress = AlertDialogUtil.initProgressDialog(this, resources.getString(R.string.label_refreshing))
        BaseMenuFragment.newInstance("enter")
        //UserChatsDTO.selectedActivity = this

        activityLentaBinding.notificationBtn.visibility =  View.INVISIBLE
        activityLentaBinding.notificationBtn.setOnClickListener(View.OnClickListener {
            //startActivity(Intent(this@LentaActivity, WriteToActivity::class.java))
            //overridePendingTransition(0, 0)
        })

        Thread {
            try {
                val contentListResponse = contentService.getTopContent(UserEntityDTO.accessToken!!, 10, this)
                UsersDTO.users = contentListResponse.users.toMutableList()
                if (contentListResponse.publications.isNotEmpty()) {
                    getTopContent(contentListResponse.publications, LentaContentDTO.publications.isEmpty())
                }
            } catch (e: java.lang.Exception) {
                //startActivity(Intent(this@LentaActivity, SelectIdentityActivity::class.java))
                //AlertDialogUtil.showErrorMessage(this@LentaActivity, e.message)
                return@Thread
            }
            try {
                 uploadLentaUsecase.execute(this, activityLentaBinding.lentaTopTableLayout)
            } catch (e: java.lang.Exception) {
                //AlertDialogUtil.showErrorMessage(this@LentaActivity, e.message, progress)
                return@Thread
            }
        }.start()


    }

    private fun getTopContent(contentList: List<PublicationApiResponse>, loading: Boolean) {

        for (contentResponse in contentList) {
            var newContent = true
            for (publication in LentaContentDTO.publications) {
                if (contentResponse.fileUUID == publication.fileUUID) {
                    newContent = false
                }
            }
            if (newContent) {
                if (loading) {
                    LentaContentDTO.publications.add(contentResponse)
                } else {
                    LentaContentDTO.publications.add(0, contentResponse)
                }
            }
        }
    }

}