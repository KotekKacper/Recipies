package com.kgkk.recipes

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CountdownTimerViewModel : ViewModel() {

    private var setTime: Long = 0
    private var startTime: Long = 0
    private val _timeLeft = MutableLiveData<Long>()
    var timeLeft: LiveData<Long> = _timeLeft

    private lateinit var timer: CountDownTimer

    fun setTimer(timeInMillis: Long) {
        setTime = timeInMillis
        startTime = setTime
        _timeLeft.value = timeInMillis
    }
    fun startTimer() {
        timer = createCountDownTimer(startTime)
        timer.start()
    }
    fun pauseTimer() {
        startTime = timeLeft.value!!
        timer.cancel()
    }
    fun stopTimer() {
        startTime = setTime
        _timeLeft.value = setTime
        timer.cancel()
    }
    private fun createCountDownTimer(timeInMillis: Long): CountDownTimer {
        return object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished
            }
            override fun onFinish() {
                _timeLeft.value = 0
            }
        }
    }
}
