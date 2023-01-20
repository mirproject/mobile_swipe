package ru.mobile.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.core.view.updateLayoutParams
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel


object ImageUtil {

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    //Создание изображения со скругленными краями, для ленты и профиля
    fun createRoundImageView(
        imageBitmap: Drawable,
        imageName: String,
        imageId: Int,
        context: Context,
        imageHeight: Int,
        radius: Float
    ): ShapeableImageView {

        val contentImageBtn = ShapeableImageView(context)
        contentImageBtn.background = imageBitmap
        contentImageBtn.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(radius)
            .build()
        contentImageBtn.scaleType = ImageView.ScaleType.CENTER_CROP
        contentImageBtn.adjustViewBounds = true
        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1F
        )
        layoutParams.setMargins(5, 5, 5, 5)
        layoutParams.weight = 1.toFloat()
        layoutParams.width = 0.toPx(context)
        layoutParams.height = imageHeight.toPx(context)
        contentImageBtn.layoutParams = layoutParams
        contentImageBtn.tag = imageName
        contentImageBtn.id = imageId

        return contentImageBtn
    }

    fun createNewTableRow(context: Context, weightSum: Float): TableRow {
        val tableRow = TableRow(context)
        tableRow.layoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tableRow.weightSum = weightSum
        return tableRow
    }

    fun createNewFrameLayout(context: Context, layoutwidth: Int, layoutHeight: Int): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = TableRow.LayoutParams(
            0, TableRow.LayoutParams.WRAP_CONTENT, 1F
        )
        layoutParams.setMargins(5, 5, 5, 5)
        frameLayout.layoutParams = layoutParams
        frameLayout.updateLayoutParams {
            width = layoutwidth.toPx(context)
            height = layoutHeight.toPx(context)
        }
        return frameLayout
    }

    fun createImageTypeIcon(context: Context, drawable: Drawable, background: Drawable): ImageView {
        val typeIcon = ImageView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        typeIcon.setImageDrawable(drawable)
        typeIcon.layoutParams = layoutParams
        typeIcon.updateLayoutParams {
            width = 150
            height = 150
        }
        typeIcon.background = background
        return typeIcon
    }

    fun createImageIcon(
        context: Context,
        drawable: Drawable,
        backgroundDrawable: Drawable,
        imageUserBitmap: Bitmap?
    ): ImageView {
        val imageIcon = ImageView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        if (imageUserBitmap == null) {
            imageIcon.setImageDrawable(drawable)
        } else {
            val imageIconBitmapResized = imageUserBitmap.fill(50)
            imageIcon.setImageBitmap(getRoundedBitmap(imageIconBitmapResized, 50))
            imageIcon.background = backgroundDrawable
            imageIcon.setPadding(2, 2, 2, 2)
        }
        layoutParams.setMargins(20, 0, 0, 20)
        imageIcon.layoutParams = layoutParams
        imageIcon.updateLayoutParams {
            width = 40.toPx(context)
            height = 40.toPx(context)
        }
        return imageIcon
    }

    fun getRoundedBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val inpBitmap = bitmap
        var width = 0
        var height = 0
        width = inpBitmap.width
        height = inpBitmap.height
        if (width <= height) {
            height = width
        } else {
            width = height
        }
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val roundPx = pixels.toFloat()
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        //paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(inpBitmap, rect, rect, paint)
        return output
    }

    fun createTextIconName(
        context: Context,
        iconName: String,
        shadowColor: Int,
        textColor: Int,
        marginStart: Int
    ): TextView {
        val textIconName = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.START or Gravity.BOTTOM
        )
        layoutParams.setMargins(marginStart.toPx(context), 0, 0, 0)
        textIconName.layoutParams = layoutParams
        textIconName.text = iconName
        textIconName.setTextColor(textColor)
        textIconName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        textIconName.setTypeface(null, Typeface.BOLD)
        textIconName.setShadowLayer(5F, 2F, 2F, shadowColor)
        textIconName.isAllCaps = false
        textIconName.updateLayoutParams {
            width = 100.toPx(context)
            height = 55.toPx(context)
        }
        textIconName.gravity = Gravity.CENTER_VERTICAL
        return textIconName
    }


}