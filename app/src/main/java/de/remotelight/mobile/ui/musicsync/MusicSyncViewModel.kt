package de.remotelight.mobile.ui.musicsync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicSyncViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is musicsync Fragment"
    }
    val text: LiveData<String> = _text
}