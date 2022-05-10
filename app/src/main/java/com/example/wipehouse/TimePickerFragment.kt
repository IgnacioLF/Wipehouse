package com.example.wipehouse

import android.app.Dialog
import android.app.DialogFragment
import android.widget.TimePicker
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import java.util.*


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()))

    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
    }
}