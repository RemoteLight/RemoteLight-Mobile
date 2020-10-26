package de.remotelight.mobile.ui.animations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.RemoteLightCore

class AnimationsViewModel : ViewModel() {

    private val animations: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(loadAnimations())
    }

    fun getAnimations() : LiveData<List<String>> = animations

    private fun loadAnimations(): List<String> {
        val animationList = RemoteLightCore.getInstance().animationManager.animations
        return animationList.map { it.displayname }
    }

}