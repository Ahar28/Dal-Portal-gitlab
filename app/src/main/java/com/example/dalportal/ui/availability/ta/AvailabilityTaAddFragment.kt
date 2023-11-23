package com.example.dalportal.ui.availability.ta

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.dalportal.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AvailabilityTaAddFragment : Fragment() {

    private lateinit var btnSelectDate1: Button
    private lateinit var btnSelectDate2: Button
    private lateinit var radioGroupDays: RadioGroup
    private lateinit var etComment: EditText
    private lateinit var btnAddRangeSet: Button
    private lateinit var linearLayoutInnerTimeRanges: LinearLayout
    private lateinit var spinnerDays: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private val timeRangeButtonsMap = mutableMapOf<String, MutableList<Pair<Button, Button>>>()

    private var selectedStartDate = getCurrentDate()
    private var selectedEndDate = getCurrentDate()

    private fun initializeTimeRangeButtonsMap() {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

        for (day in daysOfWeek) {
            timeRangeButtonsMap[day] = mutableListOf()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_availability_ta_add, container, false)

        initializeTimeRangeButtonsMap()

        // Section 1
        btnSelectDate1 = view.findViewById(R.id.btnSelectDate1)
        btnSelectDate2 = view.findViewById(R.id.btnSelectDate2)

        btnSelectDate1.text = selectedStartDate
        btnSelectDate2.text = selectedEndDate

        btnSelectDate1.setOnClickListener {
            showDatePickerDialog(btnSelectDate1)
        }

        btnSelectDate2.setOnClickListener {
            showDatePickerDialog(btnSelectDate2)
        }

        // Initialize views
        spinnerDays = view.findViewById(R.id.spinnerDays)

        // Dummy data for the spinner
        val daysArray = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, daysArray)

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerDays.adapter = spinnerAdapter

        // Set a listener to handle item selection
        spinnerDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedDay = spinnerAdapter.getItem(position)
                Toast.makeText(requireContext(), "Selected Day: $selectedDay", Toast.LENGTH_SHORT).show()
                linearLayoutInnerTimeRanges.removeAllViews()

                for (buttonPair in timeRangeButtonsMap[selectedDay]!!) {
                    // Create a LinearLayout to wrap the buttons
                    val timeRangeLayout = LinearLayout(requireContext())
                    timeRangeLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    timeRangeLayout.orientation = LinearLayout.HORIZONTAL

                    // Create a TextView between the buttons
                    val timeRangeSeparator = TextView(requireContext())
                    timeRangeSeparator.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    timeRangeSeparator.text = " - "

                    val parentButton1Layout = buttonPair.first.parent as? ViewGroup
                    parentButton1Layout?.removeView(buttonPair.first)

                    val parentButton2Layout = buttonPair.second.parent as? ViewGroup
                    parentButton2Layout?.removeView(buttonPair.second)
                    // Add views to the layout
                    timeRangeLayout.addView(buttonPair.first)
                    timeRangeLayout.addView(timeRangeSeparator)
                    timeRangeLayout.addView(buttonPair.second)

                    // Add the layout to the parent layout
                    linearLayoutInnerTimeRanges.addView(timeRangeLayout)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }

        // Section 3
        btnAddRangeSet = view.findViewById(R.id.btnAddRangeSet)
        linearLayoutInnerTimeRanges = view.findViewById(R.id.linearLayoutInnerTimeRanges)

        btnAddRangeSet.setOnClickListener {
            addTimeRangeSetButton()
        }

        // Section 4
        etComment = view.findViewById(R.id.etComment)

        return view
    }

    private fun showDatePickerDialog(targetButton: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"

                if (targetButton.id == R.id.btnSelectDate1 && isStartDateBeforeToday(selectedDate)) {
                    Toast.makeText(requireContext(), "Start date cannot be before today", Toast.LENGTH_SHORT).show()
                    return@OnDateSetListener
                }

                if (targetButton.id == R.id.btnSelectDate1 && isStartDateAfterEndDate(selectedDate, selectedEndDate)) {
                    Toast.makeText(requireContext(), "Start date cannot be after end date", Toast.LENGTH_SHORT).show()
                    return@OnDateSetListener
                }
                if (targetButton.id == R.id.btnSelectDate2 && isStartDateAfterEndDate(selectedStartDate, selectedDate)) {
                    Toast.makeText(requireContext(), "End date cannot be before start date", Toast.LENGTH_SHORT).show()
                    return@OnDateSetListener
                }

                targetButton.text = selectedDate

                if (targetButton.id == R.id.btnSelectDate1) {
                    selectedStartDate = selectedDate
                }
                if (targetButton.id == R.id.btnSelectDate2) {
                    selectedEndDate = selectedDate
                }
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun isStartDateAfterEndDate(startDate: String, endDate: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val dateStart = sdf.parse(startDate)
            val dateEnd = sdf.parse(endDate)

            return dateStart != null && dateEnd != null && dateStart.after(dateEnd)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    private fun isStartDateBeforeToday(startDate: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val dateStart = sdf.parse(startDate)
            val dateEnd = sdf.parse(getCurrentDate())

            return dateStart != null && dateEnd != null && dateEnd.after(dateStart)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return "$day/$month/$year"
    }

    private fun addTimeRangeSetButton() {
        val selectedDay = spinnerDays.selectedItem.toString()

        if (selectedDay.isNotEmpty()) {
            // Create a LinearLayout to wrap the buttons
            val timeRangeLayout = LinearLayout(requireContext())
            timeRangeLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            timeRangeLayout.orientation = LinearLayout.HORIZONTAL

            // Create the start time button
            val btnStartTime = Button(requireContext())
            btnStartTime.layoutParams = LinearLayout.LayoutParams(
                0, // Set layout_weight to distribute space equally
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Set layout_weight to distribute space equally
            )
            btnStartTime.text = "--:--"
            btnStartTime.setOnClickListener {
                showTimePickerDialog(btnStartTime, selectedDay)
            }

            // Create a TextView between the buttons
            val timeRangeSeparator = TextView(requireContext())
            timeRangeSeparator.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            timeRangeSeparator.text = " - "

            // Create the end time button
            val btnEndTime = Button(requireContext())
            btnEndTime.layoutParams = LinearLayout.LayoutParams(
                0, // Set layout_weight to distribute space equally
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Set layout_weight to distribute space equally
            )
            btnEndTime.text = "--:--"
            btnEndTime.setOnClickListener {
                showTimePickerDialog(btnEndTime, selectedDay)
            }

            // Store the buttons in the map for later reference
            timeRangeButtonsMap[selectedDay]?.add(Pair(btnStartTime, btnEndTime))

            // Add views to the layout
            timeRangeLayout.addView(btnStartTime)
            timeRangeLayout.addView(timeRangeSeparator)
            timeRangeLayout.addView(btnEndTime)

            // Add the layout to the parent layout
            linearLayoutInnerTimeRanges.addView(timeRangeLayout)


        } else {
            Toast.makeText(requireContext(), "Select a day first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showTimePickerDialog(targetButton: Button, selectedDay: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                targetButton.text = selectedTime
                if(!isStartTimeAfterEndTime(selectedDay)) {
                    Toast.makeText(requireContext(), "Start time cannot be after end time", Toast.LENGTH_SHORT).show()
                    targetButton.text = "--:--"
                }

            },
            hour, minute, true
        )

        timePickerDialog.show()

    }

    private fun isStartTimeAfterEndTime(selectedDay: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        val timeRangeList = timeRangeButtonsMap[selectedDay]
        if (timeRangeList != null) {
            for (pair in timeRangeList) {
                val startTime = pair.first.text.toString()
                val endTime = pair.second.text.toString()

//                if(startTime=="--:--" || endTime=="--:--") return true

                try {
                    val dateStart = sdf.parse(startTime)
                    val dateEnd = sdf.parse(endTime)

                    if (dateStart != null && dateEnd != null && dateStart.after(dateEnd)) {
                        // Do nothing
                    }
                    else {
                        return false
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }

        return true
    }


}
