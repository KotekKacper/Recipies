package com.kgkk.recipes.fragments

import android.graphics.Color
import android.media.SoundPool
import android.os.*
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kgkk.recipes.CountdownTimerViewModel
import com.kgkk.recipes.R

class CountdownTimerFragment : Fragment(), TimePickerFragment.OnTimeSetListener {

    private lateinit var timerText: TextView
    private lateinit var viewModel: CountdownTimerViewModel

    private var isStartButtonEnabled = true
    private var isPauseButtonEnabled = false
    private var isResetButtonEnabled = false

    private val soundPool: SoundPool by lazy {
        SoundPool.Builder().setMaxStreams(1).build()
    }

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

        restoreButtonStates(savedInstanceState, view)

        viewModel = ViewModelProvider(this)[CountdownTimerViewModel::class.java]
        viewModel.timeLeft.observe(viewLifecycleOwner) {
            updateCountdown()
        }

        countdownButtonHandler(view)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isStartButtonEnabled", isStartButtonEnabled)
        outState.putBoolean("isPauseButtonEnabled", isPauseButtonEnabled)
        outState.putBoolean("isResetButtonEnabled", isResetButtonEnabled)
    }


    override fun onTimeSet(hourOfDay: Int, minute: Int, second: Int) {
        val timeInMillis = (hourOfDay * 60 * 60 + minute * 60 + second) * 1000L
        viewModel.setTimer(timeInMillis)
        updateCountdown()
        this.view?.let { resetButtons(it) }
    }

    private fun restoreButtonStates(savedInstanceState: Bundle?, view: View) {
        isStartButtonEnabled = savedInstanceState?.getBoolean("isStartButtonEnabled", true) ?: true
        isPauseButtonEnabled = savedInstanceState?.getBoolean("isPauseButtonEnabled", false) ?: false
        isResetButtonEnabled = savedInstanceState?.getBoolean("isResetButtonEnabled", false) ?: false

        val timerStartButton = view.findViewById<Button>(R.id.startButton)
        val timerPauseButton = view.findViewById<Button>(R.id.pauseButton)
        val timerResetButton = view.findViewById<Button>(R.id.resetButton)
        timerStartButton.isEnabled = isStartButtonEnabled
        timerPauseButton.isEnabled = isPauseButtonEnabled
        timerResetButton.isEnabled = isResetButtonEnabled

    }

    private fun updateCountdown() {
        val timeLeft = viewModel.timeLeft.value!!
        updateCountdownText(timeLeft)
        updateCountdownSound(timeLeft)
    }

    private fun updateCountdownText(timeLeft: Long) {
        val hours = timeLeft / (1000 * 60 * 60)
        val minutes = (timeLeft % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (timeLeft % (1000 * 60)) / 1000
        val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        timerText.text = timeLeftFormatted
        if (timeLeft <= 0){
            timerText.setTextColor(Color.RED)
        }
    }

    private fun updateCountdownSound(timeLeft: Long) {
        // Play sound with increasing volume for last 10 seconds
        if (timeLeft in 0..10000) {
            val volume = (11000 - timeLeft) / 10000.0f
                    val soundId = soundPool.load(requireContext(), R.raw.tick, 1)
                    soundPool.setOnLoadCompleteListener { _, _, _ ->
                        soundPool.play(soundId, volume, volume, 1, 0, 1.0f)
                    }
        }
        // Play sound at low volume for the rest of the countdown
        else if (timeLeft >= 10000) {
                    val soundId = soundPool.load(requireContext(), R.raw.tick, 1)
                    soundPool.setOnLoadCompleteListener { _, _, _ ->
                        soundPool.play(soundId, 0.1f, 0.1f, 1, 0, 1.0f)
                    }
        }
        // Play finish sound
        if (timeLeft <= 0){
            val soundId = soundPool.load(requireContext(), R.raw.finish, 1)
            soundPool.setOnLoadCompleteListener { _, _, _ ->
                soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
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
        isStartButtonEnabled = timerStartButton.isEnabled
        isPauseButtonEnabled = timerPauseButton.isEnabled
        isResetButtonEnabled = timerResetButton.isEnabled
        timerText.setTextColor(Color.BLACK)
    }

    private fun countdownButtonHandler(view: View) {
        val timerStartButton = view.findViewById<Button>(R.id.startButton)
        val timerPauseButton = view.findViewById<Button>(R.id.pauseButton)
        val timerResetButton = view.findViewById<Button>(R.id.resetButton)
        timerStartButton.setOnClickListener {
            viewModel.startTimer()
            timerStartButton.isEnabled = false
            timerPauseButton.isEnabled = true
            timerResetButton.isEnabled = true
            isStartButtonEnabled = timerStartButton.isEnabled
            isPauseButtonEnabled = timerPauseButton.isEnabled
            isResetButtonEnabled = timerResetButton.isEnabled
        }
        timerPauseButton.setOnClickListener {
            viewModel.pauseTimer()
            updateCountdownText(viewModel.timeLeft.value!!)
            timerStartButton.isEnabled = true
            timerPauseButton.isEnabled = false
            timerResetButton.isEnabled = true
            isStartButtonEnabled = timerStartButton.isEnabled
            isPauseButtonEnabled = timerPauseButton.isEnabled
            isResetButtonEnabled = timerResetButton.isEnabled
        }
        timerResetButton.setOnClickListener {
            viewModel.stopTimer()
            updateCountdownText(viewModel.timeLeft.value!!)
            resetButtons(view)
        }
    }
}
