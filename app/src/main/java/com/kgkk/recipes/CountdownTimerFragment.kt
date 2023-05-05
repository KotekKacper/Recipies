package com.kgkk.recipes

import android.graphics.Color
import android.media.SoundPool
import android.os.*
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CountdownTimerFragment : Fragment(), TimePickerFragment.OnTimeSetListener {

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var timerText: TextView
    private lateinit var soundPool: SoundPool
    private var tickSoundId: Int = 0
    private var finishSoundId: Int = 1


    private var startingTime: Long = 0
    private var countdownTimeLeft: Long = startingTime // czas początkowy

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_countdown_timer, container, false)
        timerText = view.findViewById(R.id.timerText)

        timerText.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.setListener(this)
            dialog.show(parentFragmentManager, "countdown_time_picker")
        }

        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        tickSoundId = soundPool.load(requireContext(), R.raw.tick, 1)
        finishSoundId = soundPool.load(requireContext(), R.raw.finish, 1)


        countdownTimer = createCountDownTimer()
        countdownButtonHandler(view)
        return view
    }

    override fun onTimeSet(hourOfDay: Int, minute: Int, second: Int) {
        val timeInMillis = (hourOfDay * 60 * 60 + minute * 60 + second) * 1000L
        startingTime = timeInMillis
        countdownTimeLeft = timeInMillis
        countdownTimer = createCountDownTimer()
        updateCountdownText()
        this.view?.let { resetButtons(it) }
    }


    private fun updateCountdownText() {
        val hours = countdownTimeLeft / (1000 * 60 * 60)
        val minutes = (countdownTimeLeft % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (countdownTimeLeft % (1000 * 60)) / 1000
        val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        timerText.text = timeLeftFormatted
    }

    fun createCountDownTimer(): CountDownTimer {
        if (::countdownTimer.isInitialized){
            countdownTimer.cancel()
        }
        var secondsLeft = countdownTimeLeft / 1000 // Keep track of seconds left
        return object : CountDownTimer(countdownTimeLeft, 1000){
            override fun onTick(millisUntilFinished: Long) {
                secondsLeft-- // Decrement seconds left
                countdownTimeLeft = millisUntilFinished
                updateCountdownText()

                // Play sound with increasing volume for last 10 seconds
                if (secondsLeft in 0..10) {
                    val volume = (10 - secondsLeft) / 10.0f
                    val soundId = soundPool.load(requireContext(), R.raw.tick, 1)
                    soundPool.setOnLoadCompleteListener { _, _, _ ->
                        soundPool.play(soundId, volume, volume, 1, 0, 1.0f)
                    }
                }
                // Play sound at low volume for the rest of the countdown
                else if (secondsLeft >= 10) {
                    val soundId = soundPool.load(requireContext(), R.raw.tick, 1)
                    soundPool.setOnLoadCompleteListener { _, _, _ ->
                        soundPool.play(soundId, 0.1f, 0.1f, 1, 0, 1.0f)
                    }
                }
            }

            override fun onFinish() {
                // Play finish sound
                val soundId = soundPool.load(requireContext(), R.raw.finish, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
                }
                timerText.setTextColor(Color.RED)
            }
        }
    }


    private fun resetButtons(view: View){
        val timerStartButton = view.findViewById<Button>(R.id.startButton)
        val timerPauseButton = view.findViewById<Button>(R.id.pauseButton)
        val timerResetButton = view.findViewById<Button>(R.id.resetButton)
        timerStartButton.isEnabled = true
        timerPauseButton.isEnabled = false
        timerResetButton.isEnabled = false
        timerText.setTextColor(Color.BLACK)
    }

    private fun countdownButtonHandler(view: View) {
        val timerStartButton = view.findViewById<Button>(R.id.startButton)
        val timerPauseButton = view.findViewById<Button>(R.id.pauseButton)
        val timerResetButton = view.findViewById<Button>(R.id.resetButton)
        timerStartButton.setOnClickListener {
            countdownTimer.start()
            timerStartButton.isEnabled = false
            timerPauseButton.isEnabled = true
            timerResetButton.isEnabled = true
        }
        timerPauseButton.setOnClickListener {
            countdownTimer = createCountDownTimer()
            timerStartButton.isEnabled = true
            timerPauseButton.isEnabled = false
            timerResetButton.isEnabled = true
        }
        timerResetButton.setOnClickListener {
            countdownTimeLeft = startingTime
            countdownTimer = createCountDownTimer()
            updateCountdownText()
            resetButtons(view)
        }
    }
}
