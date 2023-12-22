package com.example.yougoapp.ui.pose

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.yougoapp.R
import com.example.yougoapp.adapter.Item
import com.example.yougoapp.data.State
import com.example.yougoapp.factory.ViewModelFactory
import java.util.Locale

class PlayActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var imageView: ImageView
    private lateinit var timerTextView: TextView
    private lateinit var voice: ImageButton
    private lateinit var music: ImageButton
    private lateinit var stepTextView: TextView
    private var countDownTimer: CountDownTimer? = null
    private lateinit var detailItems: List<Item>
    private var currentIndex = 0
    var id: String? = null
    var title: String? = ""
    var image: String? = ""
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val EXTRA_ID = "id"
        const val LANGUAGE_CODE_INDONESIAN = "id-ID"
        const val EXTRA_IMAGE = "ExtraImage"
        const val EXTRA_STEP = "ExtraStep"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        id = intent.getStringExtra(EXTRA_ID)
        imageView = findViewById(R.id.imageView)
        stepTextView = findViewById(R.id.stepYoga)
        timerTextView = findViewById(R.id.timerTextView)


        detailItems = emptyList()
        viewModel.detailPose(id ?: "").observe(this) { result ->
            when (result) {
                is State.Loading -> {

                }

                is State.Success -> {
                    image = result.data.pose.imageUrl
                    title = result.data.pose.title
                    val data = result.data.pose.detail.map {
                        Item(
                            stepId = it.stepId,
                            step = it.step,
                            image = it.image,
                            time = it.time
                        )
                    }

                    detailItems = data

                    showItem(currentIndex)
                    startTimer(detailItems[currentIndex].time)

                }

                is State.Error -> {

                }
            }
        }
        val nextButton = findViewById<ImageButton>(R.id.next)
        val prevButton = findViewById<ImageButton>(R.id.pref)
        voice = findViewById<ImageButton>(R.id.voiceButton)
        music = findViewById<ImageButton>(R.id.musicBtn)

        nextButton.setOnClickListener { onNextButtonClick() }
        prevButton.setOnClickListener { onPreviousButtonClick() }
        voice.setOnClickListener { onVoiceButtonClick() }
        music.setOnClickListener { onMusicButtonClick() }
        mediaPlayer = MediaPlayer.create(this, R.raw.yoga_relax)
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langResult = textToSpeech.setLanguage(Locale(LANGUAGE_CODE_INDONESIAN))
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TextToSpeech", "Language not supported")
                }
            } else {
                Log.e("TextToSpeech", "Initialization failed")
            }
        }

    }

    private fun onNextButtonClick() {

        countDownTimer?.cancel()

        if (currentIndex < detailItems.size - 1) {
            currentIndex++
            showItem(currentIndex)
            startTimer(detailItems[currentIndex].time)
        } else {

        }
    }

    private fun onPreviousButtonClick() {

        countDownTimer?.cancel()

        if (currentIndex > 0) {
            currentIndex--
            showItem(currentIndex)
            startTimer(detailItems[currentIndex].time)
        } else {

        }
    }

    private fun showItem(index: Int) {
        countDownTimer?.cancel()
        val currentItem = detailItems[index]
        Glide.with(this).load(currentItem.image).into(imageView)
        startTimer(currentItem.time)
        stepTextView.text = currentItem.step
        textToSpeech.speak(currentItem.step, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    private fun startTimer(durationMinutes: Long) {
        countDownTimer?.cancel()
        val durationMillis = durationMinutes * 60 * 70
        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                timerTextView.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {

                currentIndex++
                if (currentIndex < detailItems.size) {
                    showItem(currentIndex)
                } else {
                    val intent = Intent(this@PlayActivity, DetailPoseActivity::class.java)
                    intent.putExtra(DetailPoseActivity.EXTRA_ID, id)
                    intent.putExtra(DetailPoseActivity.EXTRA_PHOTO, image)
                    intent.putExtra(DetailPoseActivity.EXTRA_TITLE, title)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    private fun onVoiceButtonClick() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
            voice.setImageResource(R.drawable.baseline_volume_off_24)
        } else {
            val textToRead = stepTextView.text.toString()
            if (textToRead.isNotEmpty()) {
                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            }
            voice.setImageResource(R.drawable.baseline_volume_up_24)
        }
    }

    private fun onMusicButtonClick() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            music.setImageResource(R.drawable.baseline_music_off_24)
        } else {
            if (mediaPlayer.currentPosition == mediaPlayer.duration) {

                mediaPlayer.seekTo(0)
            }
            mediaPlayer.start()
            music.setImageResource(R.drawable.baseline_music_note_24)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        countDownTimer?.cancel()
        countDownTimer = null
        mediaPlayer.release()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
