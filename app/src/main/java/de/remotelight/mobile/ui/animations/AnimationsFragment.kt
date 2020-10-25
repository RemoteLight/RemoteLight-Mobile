package de.remotelight.mobile.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.remotelight.mobile.R

class AnimationsFragment : Fragment() {

    private lateinit var animationsViewModel: AnimationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        animationsViewModel =
                ViewModelProvider(this).get(AnimationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_animations, container, false)
        val textView: TextView = root.findViewById(R.id.text_animations)
        animationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}