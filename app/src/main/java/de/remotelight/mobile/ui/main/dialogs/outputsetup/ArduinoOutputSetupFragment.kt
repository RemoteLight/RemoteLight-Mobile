package de.remotelight.mobile.ui.main.dialogs.outputsetup

import android.widget.LinearLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import de.lars.remotelightcore.devices.arduino.ComPort
import de.lars.remotelightcore.out.Output
import de.remotelight.mobile.R

class ArduinoOutputSetupFragment : OutputSetupFragment() {

    private var fieldComPorts: TextInputLayout? = null
    private var comPortIndex: Int = -1

    override fun onInitLayout(layout: LinearLayout) {
        fieldComPorts = createClickableInputField(requireContext(), getString(R.string.label_com_port))
        fieldComPorts?.let {
            it.editText?.setOnClickListener {
                showComPortDialog()
            }
            layout.addView(it)
        }
    }

    override fun onSaveOutput(output: Output) {
        // TODO: save to output
    }

    private fun showComPortDialog() {
        var selectedComPort = 0
        // TODO: remove com port due jSerialComm does not support android yet
        val comPorts = try {
            ComPort.getComPorts().map { it.systemPortName }.toTypedArray()
        } catch (e: UnsatisfiedLinkError) {
            emptyArray<String>()
        }

        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.label_rgb_order))
            .setNeutralButton(getString(R.string.action_cancel)) { dialog, which ->
                dialog.dismiss() // do nothing and close dialog
            }
            .setPositiveButton(getString(R.string.action_ok)) { dialog, which ->
                // TODO
                dialog.dismiss()
            }
        // check if there are com ports available
        if (comPorts.isNotEmpty()) {
            dialogBuilder.setSingleChoiceItems(comPorts, selectedComPort) { dialog, which ->
                selectedComPort = which
            }
        } else {
            dialogBuilder.setMessage(getString(R.string.error_no_comports))
        }
        dialogBuilder.show()
    }

}