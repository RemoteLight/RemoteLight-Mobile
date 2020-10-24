package de.remotelight.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.musicsync.MusicSyncManager
import de.lars.remotelightcore.musicsync.sound.SoundProcessing
import de.lars.remotelightcore.utils.DirectoryUtil

class MainActivity : AppCompatActivity() {

    // static constants
    companion object {

        /** RemoteLightCore root directory */
        const val CORE_DIRECTORY = "RemoteLight"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up RemoteLightCore directory
        DirectoryUtil.setRootPath(filesDir.absolutePath)
        DirectoryUtil.DATA_DIR_NAME = CORE_DIRECTORY
        MusicSyncManager.initNativeSound = false

        // initialize RemoteLightCore
        val core: RemoteLightCore = RemoteLightCore(null, true)
    }

}