package de.remotelight.mobile.ui.main.dialogs.outputsetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import de.lars.remotelightcore.devices.arduino.ComPort
import de.lars.remotelightcore.out.Output
import de.remotelight.mobile.R

class ArduinoOutputSetupFragment : OutputSetupFragment() {

    private var fieldComPorts: TextInputLayout? = null
    private var comPortIndex: Int = -1

    override fun onInitLayout(layout: LinearLayout) {
        val fragmentArduino = ArduinoFragment()
        parentFragmentManager.beginTransaction().add(layout.id, fragmentArduino).commit()
            }

    override fun onSaveOutput(output: Output) {
        // TODO: save to output
    }

}

class ArduinoFragment:Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fieldSerialPort=container?.findViewById<TextInputLayout>(R.id.tfSerialPort)
        fieldSerialPort?.let {
            it.editText?.setOnClickListener {
                showComPortDialog()
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
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