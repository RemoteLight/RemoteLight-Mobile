package de.remotelight.mobile.ui.main

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.remotelight.mobile.databinding.LayoutAddOutputBinding
import de.remotelight.mobile.utils.Converter.pxToDp

class AddOutputFragment: BottomSheetDialogFragment() {

    companion object {
        const val REQUEST_KEY = "output_selected"
        const val KEY_DATA = "output_id"
    }

    private var _binding: LayoutAddOutputBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        // reveal background (change opacity) on slide
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if(!slideOffset.isNaN())
                    dialog.window?.setDimAmount(0.5f - ((slideOffset * -1) / 2.5f))
            }
        })
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = LayoutAddOutputBinding.inflate(inflater, container, false)
        // extend bottom sheet on small screen sizes (landscape)
        val parentHeight = parentFragment?.view?.height?.pxToDp(context)
        if(parentHeight?.let { it < 400.0f } == true && dialog is BottomSheetDialog) {
            (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lvAddOutput.setOnItemClickListener { adapterView, view, position, id ->
            val selOutputType = mainViewModel.onOutputMenuClicked(position)
            // if an output was selected, return output tile as result and dismiss dialog
            selOutputType?.let {
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_DATA to it))
                dismiss()
            }
        }

        // observe live data
        mainViewModel.getOutputsMenuStringIds().observe(viewLifecycleOwner, Observer {
            showListMenu(it)
        })
    }

    private fun showListMenu(arrayStringIds: List<Int>) {
        val stringList = arrayStringIds.map { getString(it) }
        val outputsAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, stringList) }
        binding.lvAddOutput.adapter = outputsAdapter
    }

    override fun onDismiss(dialog: DialogInterface) {
        // reset menu
        mainViewModel.resetOutputMenu()
        super.onDismiss(dialog)
    }

}