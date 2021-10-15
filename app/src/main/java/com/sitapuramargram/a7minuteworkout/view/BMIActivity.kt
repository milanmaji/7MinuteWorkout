package com.sitapuramargram.a7minuteworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sitapuramargram.a7minuteworkout.R
import com.sitapuramargram.a7minuteworkout.databinding.ActivityBMIBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"
    var currentVisibleView: String = METRIC_UNITS_VIEW

    lateinit var activityBMIBinding: ActivityBMIBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBMIBinding = ActivityBMIBinding.inflate(layoutInflater)
        setContentView(activityBMIBinding.root)

        setSupportActionBar(activityBMIBinding.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"

        }

        activityBMIBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        activityBMIBinding.btnCalculateUnits.setOnClickListener {

            if(currentVisibleView.equals((METRIC_UNITS_VIEW))){
                if (validateMetricUnits()) {

                    val heightValue: Float =
                        activityBMIBinding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float =
                        activityBMIBinding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)


                } else {
                    Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_LONG)
                        .show()
                }
            } else{
                if(validateUsUnits()){

                    val usUnitHeightValueFeet: String = activityBMIBinding.etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch: String = activityBMIBinding.etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue: Float = activityBMIBinding.etUsUnitWeight.text.toString().toFloat()


                    val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat()*12
                    val bmi = 703* (usUnitWeightValue/(heightValue*heightValue))
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_LONG)
                        .show()
                }
            }


        }

        makeVisibleMetricUnitsView()
        activityBMIBinding.rgUnits.setOnCheckedChangeListener { radioGroup, i ->
            if (i == activityBMIBinding.rbMetricUnits.id) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        activityBMIBinding.tilMetricUnitWeight.visibility = View.VISIBLE
        activityBMIBinding.tilMetricUnitHeight.visibility = View.VISIBLE

        activityBMIBinding.etMetricUnitHeight.text!!.clear()
        activityBMIBinding.etMetricUnitWeight.text!!.clear()

        activityBMIBinding.tilUsUnitWeight.visibility = View.GONE
        activityBMIBinding.llUsUnitsHeight.visibility = View.GONE
        activityBMIBinding.llDiplayBMIResult.visibility = View.INVISIBLE


    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNIT_VIEW
        activityBMIBinding.tilMetricUnitWeight.visibility = View.GONE
        activityBMIBinding.tilMetricUnitHeight.visibility = View.GONE

        activityBMIBinding.etUsUnitWeight.text!!.clear()
        activityBMIBinding.etUsUnitHeightFeet.text!!.clear()
        activityBMIBinding.etUsUnitHeightInch.text!!.clear()

        activityBMIBinding.tilUsUnitWeight.visibility = View.VISIBLE
        activityBMIBinding.llUsUnitsHeight.visibility = View.VISIBLE
        activityBMIBinding.llDiplayBMIResult.visibility = View.INVISIBLE


    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        activityBMIBinding.llDiplayBMIResult.visibility = View.VISIBLE
        activityBMIBinding.tvYourBMI.visibility = View.VISIBLE
        activityBMIBinding.tvBMIValue.visibility = View.VISIBLE
        activityBMIBinding.tvBMIType.visibility = View.VISIBLE
        activityBMIBinding.tvBMIDescription.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()


        activityBMIBinding.tvBMIValue.text = bmiValue
        activityBMIBinding.tvBMIType.text = bmiLabel
        activityBMIBinding.tvBMIDescription.text = bmiDescription


    }

    private fun validateMetricUnits(): Boolean {

        var isValid = true
        if (activityBMIBinding.etMetricUnitHeight.text.toString().isEmpty()) {

            isValid = false
        } else if (activityBMIBinding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid


    }

    private fun validateUsUnits(): Boolean {

        var isValid = true
        when {
            activityBMIBinding.etUsUnitHeightFeet.text.toString().isEmpty() -> isValid = false
            activityBMIBinding.etUsUnitHeightInch.text.toString().isEmpty() -> isValid = false
            activityBMIBinding.etUsUnitWeight.text.toString().isEmpty() -> isValid = false

        }

        return isValid


    }
}