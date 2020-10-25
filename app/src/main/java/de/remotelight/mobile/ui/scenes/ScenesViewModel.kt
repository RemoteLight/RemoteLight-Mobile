package de.remotelight.mobile.ui.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScenesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is scenes Fragment"
    }
    val text: LiveData<String> = _text
}