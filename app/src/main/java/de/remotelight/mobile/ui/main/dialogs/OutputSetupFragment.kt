package de.remotelight.mobile.ui.main.dialogs

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.out.Output
import de.lars.remotelightcore.utils.OutputUtil
import de.remotelight.mobile.R
import de.remotelight.mobile.databinding.LayoutOutputSetupBinding
import de.remotelight.mobile.utils.Converter.pxToDp
import de.remotelight.mobile.utils.NumberInputFilter

class OutputSetupFragment(var output: Output?): BottomSheetDialogFragment() {

    override fun getTheme(): Int {
        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) R.style.BottomSheetDialogTheme
        else R.style.BottomSheetDialogTheme_Landscape
    }

    private var _binding: LayoutOutputSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LayoutOutputSetupBinding.inflate(inflater, container, false)
        // extend bottom sheet on small screen sizes (landscape)
        val parentHeight = parentFragment?.view?.height?.pxToDp(context)
        if(parentHeight?.let { it < 400.0f } == true && dialog is BottomSheetDialog) {
            (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // add pixels filed input filter
        binding.tfOutputPixels.editText?.filters = arrayOf(NumberInputFilter(IntRange(2, Int.MAX_VALUE)))
        // close button
        binding.btnCloseOutputSetup.setOnClickListener { dismiss() }
        // dialog title
        binding.tvOutputType.text = OutputUtil.getOutputTypeAsString(output)

        // show values of the output if not null
        output?.let {
            binding.tfOutputName.editText?.setText(it.id)
            binding.tfOutputPixels.editText?.setText(it.pixels.toString())
        }

        // observe input widgets
        binding.tfOutputName.editText?.doOnTextChanged { text, start, before, count ->
            // check if name is already in use
            if(text != null && text == output?.id && RemoteLightCore.getInstance().deviceManager.isIdUsed(text.toString())) {
                binding.tfOutputName.error = getString(R.string.error_id_already_used)
            } else {
                binding.tfOutputName.error = null
            }
        }
    }

}