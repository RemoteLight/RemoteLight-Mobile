package de.remotelight.mobile.ui.scenes

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
import de.remotelight.mobile.custom.EffectRecyclerViewAdapter

class ScenesFragment : Fragment() {

    private lateinit var scenesViewModel: ScenesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        scenesViewModel =
                ViewModelProvider(this).get(ScenesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_scenes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvScenes = view.findViewById<RecyclerView>(R.id.rvScenes)
        rvScenes.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EffectRecyclerViewAdapter()
        }

        // observe view model
        scenesViewModel.getScenes().observe(viewLifecycleOwner, Observer {
            (rvScenes.adapter as EffectRecyclerViewAdapter).setList(it)
        })
    }

}