package ru.mobile.utils

import android.content.Context
import android.text.Editable
import android.util.DisplayMetrics
import android.widget.EditText

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun Int.toPx(context: Context) = this * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT

fun EditText.setEditableText(text:String){ this.text = Editable.Factory.getInstance().newEditable(text) }

fun ByteArray.toIntArray(): IntArray {
    val intArray = IntArray(this.size)
    this.forEachIndexed { index, byte ->
        intArray[index] = byte.toInt()
    }
    return intArray
}