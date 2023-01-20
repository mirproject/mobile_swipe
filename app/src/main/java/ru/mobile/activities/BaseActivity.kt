package ru.mobile.activities

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.mobile.R
import ru.mobile.entities.dto.UserEntityDTO
import ru.mobile.entities.dto.UsersDTO

/**
 *
 * Активности общие для всех
 * @date 29.06.2022
 * @author skyhunter
 *
 */
@SuppressLint("ClickableViewAccessibility", "StaticFieldLeak")
object BaseActivity {

    private var fabBgLayout: FrameLayout? = null

    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    fun showPopupMenu(
        context: AppCompatActivity,
        view: View,
        inflater: LayoutInflater,
        translatingDistances: List<Float>
    ) {

        val popupView: View = inflater.inflate(R.layout.menu_popup, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.END, 0, 0)


    }

}