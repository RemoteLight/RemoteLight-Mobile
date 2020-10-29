package de.remotelight.mobile.ui.animations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.lars.remotelightcore.RemoteLightCore
import de.remotelight.mobile.data.DefaultSetting

class AnimationsViewModel : ViewModel() {

    private val core: RemoteLightCore by lazy { RemoteLightCore.getInstance() }

    private val animations: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(loadAnimations())
    }

    fun getAnimations(): LiveData<List<String>> = animations

    private fun loadAnimations(): List<String> {
        val animationList = core.animationManager.animations
        return animationList.map { it.displayname }
    }

    val speedData: LiveData<Int> = Transformations.map(DefaultSetting.aniSpeed) { it.get() as Int }

    fun setSpeed(speed: Int) {
        DefaultSetting.aniSpeed.value?.value = speed
        core.animationManager.delay = speed
    }

}