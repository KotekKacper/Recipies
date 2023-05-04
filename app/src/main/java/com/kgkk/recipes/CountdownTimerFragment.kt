package com.kgkk.recipes

import android.os.*
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CountdownTimerFragment : Fragment(), TimePickerFragment.OnTimeSetListener {

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var timerText: TextView
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

    private fun resetButtons(view: View){
        val timerStartButton = view.findViewById<Button>(R.id.startButton)
        val timerPauseButton = view.findViewById<Button>(R.id.pauseButton)
        val timerResetButton = view.findViewById<Button>(R.id.resetButton)
        timerStartButton.isEnabled = true
        timerPauseButton.isEnabled = false
        timerResetButton.isEnabled = false
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
