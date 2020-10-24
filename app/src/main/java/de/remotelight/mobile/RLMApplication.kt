package de.remotelight.mobile

import android.app.Application
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.musicsync.MusicSyncManager
import de.lars.remotelightcore.utils.DirectoryUtil
import de.remotelight.mobile.shared.Constants

/**
 * RemoteLightMobile Core Application class.
 * The onCreate() function fires only once per application life.
 */
class RLMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // set up RemoteLightCore directory
        DirectoryUtil.setRootPath(filesDir.absolutePath)
        DirectoryUtil.DATA_DIR_NAME = Constants.CORE_DIRECTORY
        MusicSyncManager.initNativeSound = false

        // initialize RemoteLightCore
        val core = RemoteLightCore(null, true)
    }

}