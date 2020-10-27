package de.remotelight.mobile.custom.sheet

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import de.lars.remotelightcore.RemoteLightCore
import de.remotelight.mobile.R

open class BottomSheetLayout(context: Context?, root: LinearLayout) {

    companion object {
        /** [layout_width="match_parent"] and [layout_height="math_parent"] */
        val DEFAULT_LAYOUT_PARAMS =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}

open class DefaultBottomSheetLayout(context: Context?, root: LinearLayout) : BottomSheetLayout(context, root) {

    private val sliderBrightness = BottomSheetItemSlider(context)

    init {
        // add slider item to bottom sheet
        sliderBrightness.addToLayout(root)
        // set up text view
        sliderBrightness.textView.setText(R.string.label_brightness)
        // set up slider
        val lastSpeed = RemoteLightCore.getInstance().settingsManager.getSettingObject("out.brightness").get() as Int
        sliderBrightness.slider.apply {
            // slider change listener
            addOnChangeListener { slider, value, fromUser ->
                value.toInt().let {
                    RemoteLightCore.getInstance().outputManager.brightness = it // apply brightness
                    RemoteLightCore.getInstance().settingsManager.getSettingObject("out.brightness").value = it // save new brightness value
                }
            }
            valueFrom = 0.0f
            valueTo = 100.0f
            value = lastSpeed.toFloat()
            stepSize = 1.0f
        }
    }

}

open class SpeedBottomSheetLayout(context: Context?, root: LinearLayout) : DefaultBottomSheetLayout(context, root) {

    val sliderSpeed = BottomSheetItemSlider(context)

    init {
        // add slider item to bottom sheet
        sliderSpeed.addToLayout(root)
        // set up text view
        sliderSpeed.textView.setText(R.string.label_speed)
    }

}