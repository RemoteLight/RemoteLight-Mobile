package de.remotelight.mobile.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.remotelight.mobile.R
import de.remotelight.mobile.ui.custom.EffectRecyclerViewAdapter

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
        val rvAnimations = view.findViewById<RecyclerView>(R.id.rvAmimations)
        rvAnimations.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EffectRecyclerViewAdapter()
        }

        // observe view model
        animationsViewModel.getAnimations().observe(viewLifecycleOwner, Observer {
            (rvAnimations.adapter as EffectRecyclerViewAdapter).setList(it)
        })
    }


}