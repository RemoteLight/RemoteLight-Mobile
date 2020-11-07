package de.remotelight.mobile.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import de.remotelight.mobile.custom.EffectRecyclerViewAdapter
import de.remotelight.mobile.databinding.FragmentMainBinding
import de.remotelight.mobile.utils.Converter

/**
 * Fragment that shows and set ups devices.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set up recycler view
        binding.rvOutputs.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EffectRecyclerViewAdapter()
        }

        // floating action button
        binding.fabAddOutput.setOnClickListener {
            val addOutputFragment = AddOutputFragment()
            addOutputFragment.show(parentFragmentManager, addOutputFragment.tag)
        }

        // register add-output-fragment listener
        parentFragmentManager.setFragmentResultListener(AddOutputFragment.REQUEST_KEY, viewLifecycleOwner, { requestKey, result ->
            val selectedOutput = Converter.convertOutputTitleId(result.getInt(AddOutputFragment.KEY_DATA))
            println("### selected: $selectedOutput")
            // TODO: show setup screen
        })
    }

}