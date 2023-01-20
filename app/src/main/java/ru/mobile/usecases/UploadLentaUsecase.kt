package ru.mobile.usecases

import android.app.Activity
import android.widget.TableLayout
import ru.mobile.entities.dto.LentaContentDTO
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Добавление юзеров и миниатюр контента в tableLayout
 * Пока только для ленты
 */
class UploadLentaUsecase {

    private val addContentToTable: AddContentToTableUsecase = AddContentToTableUsecase()

    fun execute(activity: Activity, tableLayout: TableLayout) {

        if (LentaContentDTO.publications.isEmpty()) {
            return
        }
        val es: ExecutorService = Executors.newCachedThreadPool()

        for (publication in LentaContentDTO.publications) es.execute(
            Runnable {
                if (publication.imageBitmapThumbnail == null) {
                    val fileUUID = if (!publication.thumbnailFileUUID.isNullOrEmpty()) {
                        publication.thumbnailFileUUID
                    } else if (!publication.fileUUID.isNullOrEmpty()) {
                        publication.fileUUID
                    } else {
                        null
                    }
                }
                activity.runOnUiThread {
                    addContentToTable.execute(activity, tableLayout)
                }
            }
        )
        es.shutdown()
    }
}