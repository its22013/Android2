package jp.ac.it_college.std.s22013.mediasample

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import jp.ac.it_college.std.s22013.mediasample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()
        val mediaFielUri = Uri.parse(
            "android.resource://${packageName}/${R.raw.mountain_stream}"
        )
        mediaPlayer?.apply {
            setDataSource(applicationContext, mediaFielUri)
            setOnPreparedListener(::mediaPlayerOnPrepared)
            setOnCompletionListener(::mediaPlayerOnCompletion)
            prepareAsync()

        }
        binding.btPlay.setOnClickListener(::onPalayButtonClick)
        binding.btBack.setOnClickListener(::onBackButtonClick)
        binding.btForward.setOnClickListener(::onForwrdButtonClick)
        binding.swLoop.setOnCheckedChangeListener(::LoopSwitchChanged)
    }
    private fun mediaPlayerOnPrepared(mediaPlayer: MediaPlayer) {
        binding.btPlay.isEnabled = true
        binding.btBack.isEnabled = true
        binding.btForward.isEnabled = true
    }
    private fun mediaPlayerOnCompletion(mediaPlayer: MediaPlayer) {
        binding.btPlay.setText(R.string.bt_play_play)
    }
    private fun onPalayButtonClick(view: View) {
        mediaPlayer?.run {
            if (isPlaying) {
                pause()
                binding.btPlay.setText(R.string.bt_play_play)
            } else {
                start()
                binding.btPlay.setText(R.string.bt_play_play)
            }
        }
    }

    override fun onStop() {
        mediaPlayer?.run {
            if (isPlaying) {
                stop()
            }
            release()
        }
        super.onStop()
    }

    private fun onBackButtonClick(view: View) {
        mediaPlayer?.seekTo(0)
    }

    private fun onForwrdButtonClick(view: View) {
        mediaPlayer?.run {
            seekTo(duration)
            if (!isPlaying) {
                binding.btPlay.setText(R.string.bt_play_pause)
                start()
            }
        }
    }
    private fun LoopSwitchChanged(buttonView: CompoundButton, isChecked: Boolean) {
        mediaPlayer?.isLooping = isChecked
    }
}