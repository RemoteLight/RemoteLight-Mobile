package de.remotelight.mobile.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
        binding.navigationViewOutputs.setNavigationItemSelectedListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_DATA to it.itemId))
            dismiss()
            true
        }
    }

}