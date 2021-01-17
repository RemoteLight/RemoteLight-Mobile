package de.remotelight.mobile.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.devices.Device
import de.lars.remotelightcore.devices.arduino.RgbOrder
import de.lars.remotelightcore.out.Output
import de.remotelight.mobile.R

class MainViewModel : ViewModel() {

    /* string resource IDs for output menu list */
    private val outputRootCategories = listOf(R.string.title_serial, R.string.title_network, R.string.title_virtual)
    private val outputSerialOutputs = listOf(R.string.title_glediator)
    private val outputNetworkOutputs = listOf(R.string.title_rlserver, R.string.title_artnet, R.string.title_e131)
    private val outputVirtualOutputs = listOf(R.string.title_virtual, R.string.title_chain, R.string.title_multi)
    /** current menu level: 0 = root, 1 = serial, 2 = network, 3 = virtual */
    private var outputsMenuId = 0

    private val outputsMenuStringIds: MutableLiveData<List<Int>> by lazy {
        MutableLiveData<List<Int>>(outputRootCategories)
    }

    fun getOutputsMenuStringIds(): LiveData<List<Int>> = outputsMenuStringIds

    /**
     * Called from the add-output menu. Handles the menu navigation and returns
     * the string resource id of the clicked output type or null if no valid output
     * were clicked.
     */
    fun onOutputMenuClicked(listPosition: Int): Int? {
        if(outputsMenuId == 0) { // root
            // navigate down to outputs submenu of clicked category
            when(listPosition) {
                0 -> outputsMenuStringIds.postValue(outputSerialOutputs)
                1 -> outputsMenuStringIds.postValue(outputNetworkOutputs)
                2 -> outputsMenuStringIds.postValue(outputVirtualOutputs)
                else -> return null
            }
            outputsMenuId = listPosition + 1
        } else { // not root -> submenu
            // return the string resource id of the clicked output title
            return when(outputsMenuId) {
                1 -> outputSerialOutputs[listPosition]
                2 -> outputNetworkOutputs[listPosition]
                3 -> outputVirtualOutputs[listPosition]
                else -> null
            }.also { resetOutputMenu() }
        }
        return null
    }

    fun resetOutputMenu() {
        outputsMenuId = 0
        outputsMenuStringIds.postValue(outputRootCategories)
    }

    /* ====================
     * Output setup dialog
     * ==================== */
    val listRgbOrder = RgbOrder.values().map { it.toString() }.toTypedArray()

    private val currSetupOutput: MutableLiveData<Output?> by lazy {
        MutableLiveData<Output?>(null)
    }

    fun getCurrentSetupOutput() = currSetupOutput as LiveData<Output?>

    fun setSetupOutput(output: Output?) {
        currSetupOutput.postValue(output)
    }

    /**
     * Get the index of the selected RGB order
     */
    fun getSelectedRgbOrder(): Int {
        currSetupOutput.value?.let {
            if(it is Device) { // TODO: add rgbOrder attribute to Output class in remotelight-core
                return it.rgbOrder.ordinal
            }
        }
        return 0
    }

    fun setRgbOrder(rgbOrderIndex: Int) {
        currSetupOutput.value?.let {
            if(it is Device) {
                it.rgbOrder = RgbOrder.values()[rgbOrderIndex]
            }
        }
    }

}