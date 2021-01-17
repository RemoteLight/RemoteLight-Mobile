package de.remotelight.mobile.ui.main.dialogs.outputsetup

import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.devices.arduino.RgbOrder
import de.lars.remotelightcore.utils.OutputUtil
import de.remotelight.mobile.R
import de.remotelight.mobile.databinding.LayoutOutputSetupBinding
import de.remotelight.mobile.ui.main.MainViewModel
import de.remotelight.mobile.utils.Converter.pxToDp
import de.remotelight.mobile.utils.NumberInputFilter

abstract class OutputSetupFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int {
        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) R.style.BottomSheetDialogTheme
        else R.style.BottomSheetDialogTheme_Landscape
    }

    private var _binding: LayoutOutputSetupBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels() // shared view model

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
        // add pixels field input filter
        binding.tfOutputPixels.editText?.filters = arrayOf(NumberInputFilter(IntRange(2, Int.MAX_VALUE)))
        // close button
        binding.btnCloseOutputSetup.setOnClickListener { dismiss() }

        // observe output live data
        mainViewModel.getCurrentSetupOutput().observe(viewLifecycleOwner, Observer {
            binding.tvOutputType.text = OutputUtil.getOutputTypeAsString(it) // dialog title
            binding.tfOutputName.editText?.setText(it?.id ?: "") // name field
            binding.tfOutputPixels.editText?.setText(it?.pixels?.toString() ?: "0") // pixels field
            binding.tfPatchShiftPixels.editText?.setText(it?.outputPatch?.shift.toString()) // shift pixels field
            binding.tfPatchClone.editText?.setText(it?.outputPatch?.clone.toString()) // clone field
            binding.switchPathMirror.isChecked = it?.outputPatch?.isCloneMirrored ?: false // mirror switch
            onRgbOrderUpdated()
            it?.let { onPixelNumberChanged(it.pixels) }
        })

        // observe input widgets
        binding.tfOutputName.editText?.doOnTextChanged { text, start, before, count ->
            val output = mainViewModel.getCurrentSetupOutput().value
            // check if name is already in use
            if(text != null && text == output?.id && RemoteLightCore.getInstance().deviceManager.isIdUsed(text.toString())) {
                binding.tfOutputName.error = getString(R.string.error_id_already_used)
            } else {
                binding.tfOutputName.error = null
            }
        }

        // show RGB order dialog when clicked on RGB order text field
        binding.tfRgbOrder.editText?.setOnClickListener { showRgbOrderDialog() }

        // add click listener to save button
        binding.btnSaveOutput.setOnClickListener {
            // TODO: save values to output
            onSaveOutput()
        }

        // setup input widgets in subclass
        onInitLayout(binding.layoutOutputSetupComps)
    }

    private fun showRgbOrderDialog() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.label_rgb_order))
                .setNeutralButton(getString(R.string.action_cancel)) { dialog, which ->
                    dialog.dismiss() // do nothing and close dialog
                }
                .setPositiveButton(getString(R.string.action_ok)) { dialog, which ->
                    // update rgb order and close dialog
                    onRgbOrderUpdated()
                    dialog.dismiss()
                }
                .setSingleChoiceItems(mainViewModel.listRgbOrder, mainViewModel.getSelectedRgbOrder()) { dialog, which ->
                    mainViewModel.setRgbOrder(which)
                }
                .show()
        }
    }

    private fun onPixelNumberChanged(pixels: Int) {
        // update filters of fields
        binding.tfPatchShiftPixels.editText?.filters = arrayOf(NumberInputFilter(IntRange(0, pixels)))
        binding.tfPatchClone.editText?.filters = arrayOf(NumberInputFilter(IntRange(0, pixels / 2)))
    }

    private fun onRgbOrderUpdated() {
        // update RGB order text field
        binding.tfRgbOrder.editText?.setText(RgbOrder.values()[mainViewModel.getSelectedRgbOrder()].name)
    }

    /**
     * Initialize the setup components and add it to the layout
     */
    abstract fun onInitLayout(layout: LinearLayout)

    /**
     * Save values from components to the output
     */
    abstract fun onSaveOutput()

    //---
    // Some helper methods for creating common input widgets

    fun createStringInputField(hint: String? = null): TextInputLayout? {
        val inputLayout = context?.let { TextInputLayout(it, null, R.style.Widget_App_TextInputLayout) }
        inputLayout?.apply {
            layoutParams = layoutParams.apply {
                width = LinearLayout.LayoutParams.MATCH_PARENT
                height = LinearLayout.LayoutParams.WRAP_CONTENT
            }
            boxBackgroundColor = ContextCompat.getColor(context, R.color.colorDialogBackground)
            setHint(hint)
        }
        return inputLayout
    }

    fun createNumberInputField(hint: String? = null): TextInputLayout? {
        val inputLayout = createStringInputField(hint)
        inputLayout?.editText?.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
        return inputLayout
    }

}