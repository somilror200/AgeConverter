package com.example.ageconverter

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var textSelectedDate: TextView? = null
    private var textConvertedAge: TextView? = null
    private var textAgeUnit : TextView? = null
    private var inMinutes : String? = null
    private var inHours : String? = null
    private var text1 : TextView? = null
    private var text2 : TextView? = null
    private var text3 : TextView? = null
    private var displayDateUnit : TextView? = null
    private var differenceInMinutes : Long? = null
    private var differenceInHours : Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        val unitSwitch : SwitchCompat = findViewById(R.id.switch1)
        textSelectedDate = findViewById(R.id.selectedDate)
        textConvertedAge = findViewById(R.id.ageInMinutes)
        text1 = findViewById(R.id.textView1)
        text2 = findViewById(R.id.textView3)
        text3 = findViewById(R.id.textView4)
        displayDateUnit = findViewById(R.id.displayedDateUnit)

        val text1Value = "CALCULATE YOUR"
        val text2value = "AGE"
        val text3value = "SELECTED DATE"
        val buttonText = "SELECT DATE"
        val minuteDateUnit = "AGE IN MINUTES"
        val hourDateUnit = "AGE IN HOURS"
        val switchHour = "HOURS"
        val switchMinute = "MINUTES"
        val nullText = ""
        text1?.text = text1Value
        text2?.text = text2value
        text3?.text = text3value
        btnDatePicker.text = buttonText

        inMinutes = "IN MINUTES"
        inHours = "IN HOURS"
        textAgeUnit = findViewById(R.id.textView2)
        textAgeUnit?.text = inMinutes
        if(textAgeUnit?.text == inMinutes)
        {
            displayDateUnit?.text = minuteDateUnit
            unitSwitch.text = switchHour
        }
        else
        {
            displayDateUnit?.text = hourDateUnit
            unitSwitch.text = switchMinute
        }

        btnDatePicker.setOnClickListener{
            clickDatePiker()
        }

        unitSwitch.setOnClickListener{
            if(unitSwitch.isChecked)
            {
                textAgeUnit?.text = inHours
                unitSwitch.text = switchMinute
                if(differenceInMinutes == null && differenceInHours == null)
                    textConvertedAge?.text = nullText
                else
                    textConvertedAge?.text = differenceInHours.toString()
                displayDateUnit?.text = hourDateUnit
            }
            else
            {
                textAgeUnit?.text = inMinutes
                unitSwitch.text = switchHour
                if(differenceInMinutes == null && differenceInHours == null)
                    textConvertedAge?.text = nullText
                else
                    textConvertedAge?.text = differenceInMinutes.toString()
                displayDateUnit?.text = minuteDateUnit
            }
        }
    }
    private fun clickDatePiker()
    {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "Date picker works", Toast.LENGTH_SHORT).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                textSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000
                    val selectedDateInHours = theDate.time / 3600000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val currentDateInHours = currentDate.time / 3600000

                        differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        differenceInHours = currentDateInHours - selectedDateInHours

                        if(textAgeUnit?.text == inMinutes)
                            textConvertedAge?.text = differenceInMinutes.toString()
                        else
                            textConvertedAge?.text = differenceInHours.toString()
                    }
                }

            }, year, month, day)

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialog.show()
    }
}