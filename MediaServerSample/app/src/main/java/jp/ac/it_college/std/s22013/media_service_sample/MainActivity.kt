package jp.ac.it_college.std.s22013.media_service_sample

import android.content.ComponentName
import android.media.MediaFeature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import jp.ac.it_college.std.s22013.media_service_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var controllerFuture: ListenableFuture<MediaController>

    private val controller : MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btPlay.setOnClickListener { playSample() }
        binding.btStop.setOnClickListener { pauseSample() }
    }

    override fun onStart() {
        super.onStart()
        controllerFuture = MediaController.Builder(
            this,
            SessionToken(this, ComponentName(this, MediaPlaybackService::class.java))
        ).buildAsync()
    }

    override fun onStop() {
        MediaController.releaseFuture(controllerFuture)
        super.onStop()
    }
    private fun playSample() {
        val controller =  this.controller ?: return

        controller.setMediaItem(
            MediaItem.fromUri("android.resource://${packageName}/${R.raw.hanabe}")
        )
        controller.seekToDefaultPosition(0)

        controller.play()
    }

    private fun pauseSample() {
        val controller =  this.controller ?: return

        controller.stop()
    }
}