package de.remotelight.mobile.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import de.remotelight.mobile.R
import de.remotelight.mobile.shared.Constants

object Converter {

    /**
     * Converts an output title id (defined in [strings.xml]) to
     * an unique output id (defined in [de.remotelight.mobile.shared.Constants])
     */
    fun convertOutputTitleId(id: Int): String? {
        return when(id) {
            R.string.title_glediator -> Constants.GLEDIATOR
            R.string.title_rlserver -> Constants.REMOTELIGHTSERVER
            R.string.title_artnet -> Constants.ARTNET
            R.string.title_e131 -> Constants.E131
            R.string.title_virtual -> Constants.VIRTUAL
            R.string.title_chain -> Constants.CHAIN
            R.string.title_multi -> Constants.MULTI
            else -> null
        }
    }

    fun Int.pxToDp(context: Context?): Float {
        return if(context != null)
            this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        else
            this / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun Int.dpToPx(context: Context?): Float {
        return if(context != null)
            this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        else
            this * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

}