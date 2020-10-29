package de.remotelight.mobile.data

import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.settings.SettingsManager
import de.lars.remotelightcore.settings.types.SettingObject

object DefaultSetting {

    private val sm: SettingsManager by lazy { RemoteLightCore.getInstance().settingsManager }

    val outBrightness = ObservableSetting<SettingObject>(sm.getSettingObject("out.brightness"), false)

    val aniSpeed = ObservableSetting<SettingObject>(SettingObject("animation.speed", null, 50))

}