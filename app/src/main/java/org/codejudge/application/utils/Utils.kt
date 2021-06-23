package org.codejudge.application.utils

import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import java.net.MalformedURLException
import java.net.URL

object Utils {
    fun String.isValidUrl(): Boolean {
        return try {
            URL(this)
            true
        } catch (ex: MalformedURLException) {
            Log.e("LogicUtils", "The provided htmlUrl: \"$this\" is invalid.")
            false
        }
    }
}

class EmojiExcludeFilter : InputFilter {

    override fun filter(charSequence: CharSequence, start: Int, end: Int, spanned: Spanned, mStart: Int, mEnd: Int): CharSequence? {
        for (i in start until end) {
            val type = Character.getType(charSequence[i])
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return ""
            }
        }
        return null
    }
}
