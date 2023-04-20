package com.domainbangla.smartpoultry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.domainbangla.smartpoultry.networking.ApiService
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ConfigureActivity : AppCompatActivity() {

    lateinit var etMinTemp: TextInputEditText
    lateinit var etMaxTemp: TextInputEditText
    lateinit var btnSave:MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure)

        initialize();

        updateUI()
    }

    fun initialize(){
        etMinTemp = findViewById(R.id.etMinTemp)
        etMaxTemp = findViewById(R.id.etMaxTemp)
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
          val inputMinTemp = etMinTemp.text
          val inputMaxTemp = etMaxTemp.text

            if (!inputMinTemp.isNullOrBlank() && !inputMaxTemp.isNullOrBlank()){
                processInput()
            }else{
                showMessage("Enter valid temperature range")
            }
        }
    }

    fun showMessage(msg:String){
        Toast.makeText(this@ConfigureActivity,msg,Toast.LENGTH_SHORT).show()
    }

    fun updateUI(){
        etMinTemp.setText(MainActivity.minTemp.toString())
        etMaxTemp.setText(MainActivity.maxTemp.toString())
    }

    fun processInput(){
        val minTemp = etMinTemp.text.toString()
        val maxTemp = etMaxTemp.text.toString()
        try {
            val minTempVal = minTemp.toInt()
            val maxTempVal = maxTemp.toInt()

            MainActivity.minTemp = minTempVal
            MainActivity.maxTemp = maxTempVal

            ApiService.configure(this@ConfigureActivity,minTempVal,maxTempVal)

        } catch (nfe: NumberFormatException) {
           showMessage("Enter valid number")
        }

    }


}