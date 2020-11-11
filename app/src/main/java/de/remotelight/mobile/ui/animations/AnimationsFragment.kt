package de.remotelight.mobile.ui.animations

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import de.remotelight.mobile.R
import de.remotelight.mobile.custom.EffectRecyclerViewAdapter
import de.remotelight.mobile.custom.sheet.SpeedBottomSheetLayout

class AnimationsFragment : Fragment() {

    private lateinit var animationsViewModel: AnimationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // set opaque navigation bar color in portrait mode
        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            activity?.let { it.window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.colorNavigationBar, null) }
        }
        animationsViewModel =
                ViewModelProvider(this).get(AnimationsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_animations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set up recycler view
        val rvAnimations = view.findViewById<RecyclerView>(R.id.rvAnimations)
        rvAnimations.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EffectRecyclerViewAdapter()
        }

        // set up bottom sheet
        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottom_sheet_holder_root)
        val bottomSheetLayout = SpeedBottomSheetLayout(context, bottomSheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetLayout.setupKnobListener(bottomSheetBehavior)

        // speed slider
        bottomSheetLayout.sliderSpeed.slider.apply {
            addOnChangeListener { slider, value, fromUser ->
                animationsViewModel.setSpeed(value.toInt())
            }
            valueFrom = 0.0f
            valueTo = 200.0f
            stepSize = 1.0f
        }

        // observe live data
        animationsViewModel.getAnimations().observe(viewLifecycleOwner, Observer {
            (rvAnimations.adapter as EffectRecyclerViewAdapter).setList(it)
        })

        animationsViewModel.speedData.observe(viewLifecycleOwner, Observer {
            bottomSheetLayout.sliderSpeed.slider.value = it.toFloat()
        })

        // bottom sheet insets
        val (initialSheetLeft, initialSheetRight, initialSheetBottom, initialPeekHeight) =
                listOf(bottomSheet.paddingLeft, bottomSheet.paddingRight, bottomSheet.paddingBottom, bottomSheetBehavior.peekHeight)
        bottomSheet.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(insets.systemWindowInsetLeft + initialSheetLeft, view.paddingTop, insets.systemWindowInsetRight + initialSheetRight, insets.systemWindowInsetBottom + initialSheetBottom)
            bottomSheetBehavior.peekHeight = insets.systemWindowInsetBottom + initialPeekHeight
            insets
        }
    }

    override fun onDestroyView() {
        // reset navigation bar color
        activity?.let { it.window.navigationBarColor = ResourcesCompat.getColor(resources, android.R.color.transparent, null) }
        super.onDestroyView()
    }

}