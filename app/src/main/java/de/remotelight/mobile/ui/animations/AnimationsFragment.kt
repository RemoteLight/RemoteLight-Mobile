package de.remotelight.mobile.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
    }


}