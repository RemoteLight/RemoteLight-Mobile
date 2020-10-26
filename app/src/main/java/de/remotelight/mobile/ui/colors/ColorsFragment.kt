package de.remotelight.mobile.ui.colors

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apandroid.colorwheel.ColorWheel
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import de.lars.remotelightcore.RemoteLightCore
import de.remotelight.mobile.R

class ColorsFragment : Fragment() {

    private lateinit var colorsViewModel: ColorsViewModel
    private var selectedColor = Color.RED

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        colorsViewModel = ViewModelProvider(this).get(ColorsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorWheel = view.findViewById<ColorWheel>(R.id.colorWheel)
        val gradientBar = view.findViewById<GradientSeekBar>(R.id.gradientBar)

        // set defaults
        colorWheel.rgb = selectedColor
        gradientBar.startColor = Color.BLACK
        gradientBar.endColor = selectedColor
        gradientBar.offset = 1.0f

        // add color wheel listener
        colorWheel.colorChangeListener = {rgb: Int ->
            selectedColor = rgb
            // update gradient bar
            gradientBar.endColor = selectedColor
            // output color
            outputColor(calcColor(rgb, gradientBar.offset))
        }

        // add gradient bar listener
        gradientBar.colorChangeListener = {offset: Float, rgb: Int ->
            // output color
            outputColor(calcColor(rgb, offset))
        }
    }

    private fun calcColor(rgb: Int, offset: Float): Int {
        val hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(rgb), Color.green(rgb), Color.blue(rgb), hsv)
        hsv[2] = offset
        return Color.HSVToColor(hsv)
    }

    private fun outputColor(rgb: Int) {
        val color = de.lars.remotelightcore.utils.color.Color(rgb)
        RemoteLightCore.getInstance().colorManager.showColor(color)
    }

}