package com.example.yougoapp.ui.pose

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yougoapp.R
import com.example.yougoapp.adapter.DetailAdapter
import com.example.yougoapp.adapter.Item
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.ActivityDetailPoseBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.detection.DetectionActivity
import com.example.yougoapp.ui.home.HomeActivity
import com.example.yougoapp.ui.schedule.DailyReminderReceiver
import com.example.yougoapp.ui.schedule.ScheduleActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DetailPoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPoseBinding
    private val viewModel by viewModels<PoseViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var isTimerPaused: Boolean = false
    private lateinit var adapter: DetailAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var countdownTimer: CountDownTimer
    private var countdownDurationMillis: Long = 0
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var detailItems: List<Item>
    private var id: Int = 0
    private var title: String? = ""
    private var day: String? = ""
    private var poseId: String? = ""

    companion object {
        const val EXTRA_PHOTO = "extraPhoto"
        const val EXTRA_ID = "extraID"
        const val LANGUAGE_CODE_INDONESIAN = "id-ID"

        const val EXTRA_CATEGORY = "extraCategory"
        const val EXTRA_TITLE = "extraTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        poseId = intent.getStringExtra(EXTRA_ID)
        val image = intent.getStringExtra(EXTRA_PHOTO)
        title = intent.getStringExtra(EXTRA_TITLE)

        binding.title.text = title
        Glide.with(this).load(image).into(binding.myImageView)

        val layoutManager = LinearLayoutManager(this)
        binding.rvDetail.layoutManager = layoutManager
        mediaPlayer = MediaPlayer.create(this, R.raw.yoga_relax)

        binding.add.setOnClickListener {
            showDayPickerDialog()

        }

        binding.bottomScan.setOnClickListener {
            val intent = Intent(this@DetailPoseActivity, DetectionActivity::class.java)
            intent.putExtra(DetectionActivity.EXTRA_ID, poseId)
            intent.putExtra(DetectionActivity.EXTRA_IMAGE, image)
            intent.putExtra(DetectionActivity.EXTRA_TITLE, title)
            startActivity(intent)
        }
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.play -> resumeCountdown()
                R.id.pause -> pauseCountdown()
            }
            true
        }
        binding.pbTimer.setOnClickListener {
            showTimePickerDialog()

        }
        binding.musicBtn.setOnClickListener {
            onMusicButtonClick()
        }
        binding.voiceButton.setOnClickListener {
            toggleTextToSpeech()
        }
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale(LANGUAGE_CODE_INDONESIAN)
            }
        }
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        viewModel.detailPose(poseId ?: "").observe(this) { detail ->
            if (detail != null) {
                when (detail) {
                    is State.Loading -> {
                    }

                    is State.Success -> {
                        val pose = detail.data.pose.detail
                        adapter = DetailAdapter(poseId ?: "", pose)
                        binding.rvDetail.adapter = adapter
                        val data = detail.data.pose.detail.map {
                            Item(
                                stepId = it.stepId,
                                step = it.step,
                                image = it.image,
                                time = it.time
                            )
                        }

                        detailItems = data

                    }

                    is State.Error -> {
                        Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun readAllSteps() {
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale(LANGUAGE_CODE_INDONESIAN)
                for (item in detailItems) {
                    textToSpeech.speak(item.step, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }

    private fun showDayPickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, _, _, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(currentYear, currentMonth, dayOfMonth)
                day = SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedCalendar.time)

                viewModel.addSchedule(id, title ?: "", poseId ?: "", day ?: "")

                val intent = Intent(this@DetailPoseActivity, ScheduleActivity::class.java)
                startActivity(intent)
            },
            currentYear,
            currentMonth,
            currentDay
        )


        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        calendar.add(Calendar.YEAR, 1)
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
        datePickerDialog.setOnDismissListener {
            scheduleDailyReminder()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentSecond = calendar.get(Calendar.SECOND)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedMinute, selectedSecond ->
                countdownDurationMillis =
                    (selectedMinute * 60 + selectedSecond) * 1000L
                startCountdown(countdownDurationMillis)
            },
            currentMinute,
            currentSecond,
            true
        )

        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }

    private fun startCountdown(durationMillis: Long) {

        if (!::textToSpeech.isInitialized) {
            textToSpeech = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.language = Locale(LANGUAGE_CODE_INDONESIAN)
                }
            }
        }

        countdownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = millisUntilFinished / 1000 / 60
                val secondsRemaining = millisUntilFinished / 1000 % 60
                binding.timer.text = String.format(
                    "%02d:%02d",
                    minutesRemaining,
                    secondsRemaining
                )
            }

            override fun onFinish() {
                binding.timer.text = "Finish"
                startMusic()
            }
        }
        countdownTimer.start()
        readAllSteps()
        startMusic()
        startTextToSpeech()
    }

    private fun pauseCountdown() {
        if (::countdownTimer.isInitialized && countdownTimer != null) {
            countdownTimer.cancel()
            isTimerPaused = true
            pauseMusic()
            stopTextToSpeech()
        }
    }

    private fun resumeCountdown() {
        if (::countdownTimer.isInitialized && countdownTimer != null && isTimerPaused) {
            if (!::textToSpeech.isInitialized) {
                textToSpeech = TextToSpeech(this) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        textToSpeech.language = Locale(LANGUAGE_CODE_INDONESIAN)
                    }
                }
            }

            startCountdown(countdownDurationMillis)
            isTimerPaused = false
            startMusic()
            startTextToSpeech()
        }
    }

    private fun startMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.musicBtn.setImageResource(R.drawable.baseline_music_note_24)
        }
    }

    private fun scheduleDailyReminder() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dailyReminderIntent = Intent(this, DailyReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            dailyReminderIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            binding.musicBtn.setImageResource(R.drawable.baseline_music_off_24)
        }
    }

    private fun onMusicButtonClick() {
        if (mediaPlayer.isPlaying) {
            pauseMusic()
        } else {
            if (mediaPlayer.currentPosition == mediaPlayer.duration) {
                mediaPlayer.seekTo(0)
            }
            startMusic()
        }

    }

    private fun toggleTextToSpeech() {
        if (::textToSpeech.isInitialized) {
            if (textToSpeech.isSpeaking) {
                stopTextToSpeech()

            } else {
                startTextToSpeech()

            }
        }
    }

    private fun stopTextToSpeech() {
        textToSpeech.stop()
        binding.voiceButton.setImageResource(R.drawable.baseline_volume_off_24)
    }

    private fun startTextToSpeech() {
        readAllSteps()
        binding.voiceButton.setImageResource(R.drawable.baseline_volume_up_24)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }
        textToSpeech.shutdown()
        mediaPlayer.release()
    }


}


