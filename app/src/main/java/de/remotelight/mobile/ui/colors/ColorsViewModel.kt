package de.remotelight.mobile.ui.colors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.utils.color.Color

class ColorsViewModel : ViewModel() {

    private val currentColor: MutableLiveData<Color> by lazy {
        MutableLiveData<Color>(getLastColor())
    }

    fun getColor(): LiveData<Color> = currentColor

    fun setColor(color: Color) {
        currentColor.postValue(color)
    }

    private fun getLastColor(): Color {
        return RemoteLightCore.getInstance().colorManager.lastColor?: Color.RED
    }

}