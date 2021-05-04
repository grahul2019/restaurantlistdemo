package org.codejudge.application.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.codejudge.application.BuildConfig
import org.codejudge.application.R
import org.codejudge.application.domain.model.RestaurantImageParams
import org.codejudge.application.utils.Utils.isValidUrl

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

fun AppCompatImageView.loadImage(imageParams: RestaurantImageParams?) {
    imageParams?.let {

        fun imagePlaceholderResource(): Int {
            return  R.drawable.ic_image_placeholder
        }

        fun imageFailureResource(): Int {
            return R.drawable.ic_image_error_placeholder
        }

        fun makeImageUrl(width: Int?, height: Int?, photoReference: String?):String{
            return "${BuildConfig.APP_URL}photo?maxwidth=${width}&maxheight=${height}&photoreference=${photoReference}&key=${BuildConfig.API_KEY}"
        }

        val url = makeImageUrl(imageParams.width,imageParams.height,imageParams.photoReference)

        // Making sure that the provided HTML_URL is valid before loading the image.
        if (url.isValidUrl()) {
            Glide.with(context)
                .load(url)
                .placeholder(imagePlaceholderResource())
                .into(this)
        } else {
            Glide.with(context).load(imageFailureResource()).into(this)
        }
    }
}