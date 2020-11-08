package de.remotelight.mobile.custom.sheet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.slider.Slider
import de.remotelight.mobile.R

open class BottomSheetItem(context: Context?) {

}

open class BottomSheetItemKnob(context: Context?, root: ViewGroup) : BottomSheetItem(context) {

    val ivKnob: ImageView = root.findViewById(R.id.ivSheetKnob)

}

open class BottomSheetItemSlider(context: Context?) : BottomSheetItem(context) {

    private val viewSlider: View = LayoutInflater.from(context).inflate(R.layout.layout_slider, null)
    val slider: Slider = viewSlider.findViewById(R.id.slider_normal)
    val textView: TextView = viewSlider.findViewById(R.id.textView_slider)

    fun addToLayout(root: ViewGroup) {
        // add slider layout to root view
        root.addView(viewSlider)
    }

}