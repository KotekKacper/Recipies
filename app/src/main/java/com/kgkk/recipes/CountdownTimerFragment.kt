package com.kgkk.recipes

import android.os.*
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CountdownTimerFragment : Fragment() {

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var timerText: TextView
    private var startingTime: Long = 60000
    private var countdownTimeLeft: Long = startingTime // czas początkowy

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_countdown_timer, container, false)
        timerText = view.findViewById(R.id.timerText)
        countdownTimer = createCountDownTimer()
        countdownButtonHandler(view)
        return view
    }

    private fun updateCountdownText() {
        val hours = countdownTimeLeft / (1000 * 60 * 60)
        val minutes = (countdownTimeLeft % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (countdownTimeLeft % (1000 * 60)) / 1000
        val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        timerText.text = timeLeftFormatted
    }

    fun createCountDownTimer(): CountDownTimer {
        return object : CountDownTimer(countdownTimeLeft, 1000){
            override fun onTick(millisUntilFinished: Long) {
                countdownTimeLeft = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                // sygnał dźwiękowy
            }
        }
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
            countdownTimer.cancel()
            countdownTimer = createCountDownTimer()
            timerStartButton.isEnabled = true
            timerPauseButton.isEnabled = false
            timerResetButton.isEnabled = true
        }
        timerResetButton.setOnClickListener {
            countdownTimer.cancel()
            countdownTimeLeft = startingTime
            countdownTimer = createCountDownTimer()
            updateCountdownText()
            timerStartButton.isEnabled = true
            timerPauseButton.isEnabled = false
            timerResetButton.isEnabled = false
        }
    }
}
