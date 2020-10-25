package de.remotelight.mobile.ui.animations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnimationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is animations Fragment"
    }
    val text: LiveData<String> = _text
}