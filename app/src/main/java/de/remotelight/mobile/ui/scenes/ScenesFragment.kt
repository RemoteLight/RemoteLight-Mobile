package de.remotelight.mobile.ui.scenes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.remotelight.mobile.R

class ScenesFragment : Fragment() {

    private lateinit var scenesViewModel: ScenesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        scenesViewModel =
                ViewModelProvider(this).get(ScenesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_scenes, container, false)
        val textView: TextView = root.findViewById(R.id.text_scenes)
        scenesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}