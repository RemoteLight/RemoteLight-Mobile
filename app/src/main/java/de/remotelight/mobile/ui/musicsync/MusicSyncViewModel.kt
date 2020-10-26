package de.remotelight.mobile.ui.musicsync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.RemoteLightCore

class MusicSyncViewModel : ViewModel() {

    private val musicSync: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(loadMusicSync())
    }

    fun getMusicEffects() : LiveData<List<String>> = musicSync

    private fun loadMusicSync(): List<String> {
        val musicSyncList = RemoteLightCore.getInstance().musicSyncManager.musicEffects
        return musicSyncList.map { it.displayname }
    }
}