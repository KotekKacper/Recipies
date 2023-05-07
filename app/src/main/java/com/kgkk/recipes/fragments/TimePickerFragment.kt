package com.kgkk.recipes.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kgkk.recipes.R

class TimePickerFragment : DialogFragment() {

    private lateinit var timePickerHour: NumberPicker
    private lateinit var timePickerMin: NumberPicker
    private lateinit var timePickerSec: NumberPicker
    private var listener: OnTimeSetListener? = null

    interface OnTimeSetListener {
        fun onTimeSet(hourOfDay: Int, minute: Int, second: Int)
    }

    fun setListener(listener: OnTimeSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_time_picker, null)
        timePickerHour = view?.findViewById(R.id.numpicker_hours)!!
        timePickerHour.minValue = 0
        timePickerHour.value = 0
        timePickerHour.maxValue = 23
        timePickerMin = view.findViewById(R.id.numpicker_minutes)!!
        timePickerMin.minValue = 0
        timePickerMin.value = 0
        timePickerMin.maxValue = 59
        timePickerSec = view.findViewById(R.id.numpicker_seconds)!!
        timePickerSec.minValue = 0
        timePickerSec.value = 0
        timePickerSec.maxValue = 59

        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.set_countdown_time)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                listener?.onTimeSet(timePickerHour.value, timePickerMin.value, timePickerSec.value)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}
