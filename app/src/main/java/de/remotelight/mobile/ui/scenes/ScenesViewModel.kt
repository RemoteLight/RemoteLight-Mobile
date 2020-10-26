package de.remotelight.mobile.ui.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.RemoteLightCore

class ScenesViewModel : ViewModel() {

    private val scenes: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(loadScenes())
    }

    fun getScenes() : LiveData<List<String>> = scenes

    private fun loadScenes(): List<String> {
        val scenesList = RemoteLightCore.getInstance().sceneManager.scenes
        return scenesList.map { it.displayname }
    }
}