package de.remotelight.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.lars.remotelightcore.RemoteLightCore
import de.lars.remotelightcore.musicsync.sound.SoundProcessing

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val core: RemoteLightCore = RemoteLightCore(null, true)
    }

}