package de.remotelight.mobile.data

import androidx.lifecycle.MutableLiveData
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.settings.Setting
import de.lars.remotelightcore.settings.SettingsManager

open class ObservableSetting<T: Setting>(private var setting: T, private val register: Boolean = true): MutableLiveData<T>(setting) {

    private val sm: SettingsManager = RemoteLightCore.getInstance().settingsManager

    init {
        if(register)
            setting = sm.addSetting(setting)
        // add setting change listener
        setting.setValueListener {s ->
            postValue(setting)
        }
    }

}