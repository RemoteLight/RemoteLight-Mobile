package de.remotelight.mobile.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import de.remotelight.mobile.R
import de.remotelight.mobile.shared.Constants

object Converter {

    /**
     * Converts an output menu id (defined in [res/menu/add_output_nav_menu]) to
     * an unique output id (defined in [de.remotelight.mobile.shared.Constants])
     */
    fun convertOutputMenuId(id: Int): String? {
        return when(id) {
            R.id.nav_output_glediator -> Constants.GLEDIATOR
            R.id.nav_output_rlserver -> Constants.REMOTELIGHTSERVER
            R.id.nav_output_artnet -> Constants.ARTNET
            R.id.nav_output_e131 -> Constants.E131
            R.id.nav_output_virtual -> Constants.VIRTUAL
            R.id.nav_output_chain -> Constants.CHAIN
            R.id.nav_output_multi -> Constants.MULTI
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