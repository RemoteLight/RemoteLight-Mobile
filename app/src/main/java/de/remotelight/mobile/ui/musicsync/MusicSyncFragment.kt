package de.remotelight.mobile.ui.musicsync

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
import de.remotelight.mobile.utils.addSystemWindowInsetToPadding

class MusicSyncFragment : Fragment() {

    private lateinit var musicSyncViewModel: MusicSyncViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        musicSyncViewModel =
                ViewModelProvider(this).get(MusicSyncViewModel::class.java)
        return inflater.inflate(R.layout.fragment_musicsync, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvMusicSync = view.findViewById<RecyclerView>(R.id.rvMusicSync)
        rvMusicSync.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EffectRecyclerViewAdapter()
        }

        // observe view model
        musicSyncViewModel.getMusicEffects().observe(viewLifecycleOwner, Observer {
            (rvMusicSync.adapter as EffectRecyclerViewAdapter).setList(it)
        })

        // recycler view insets
        rvMusicSync.addSystemWindowInsetToPadding(left = true, right = true, bottom = true)
    }

}