package org.codejudge.application.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

/**
 *  Display Snackbar
 *
 */
fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, message, duration)
}

fun FragmentActivity.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String, isLong: Boolean = false) {
    requireActivity().toast(message, isLong)
}

fun View.remove() {
    if (visibility != View.GONE) visibility = View.GONE
}

fun View.show() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun FragmentActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).runCatching {
            hideSoftInputFromWindow(it.windowToken, 0)
        }.onFailure {
        }
    }
}