package com.domainbangla.smartpoultry

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.domainbangla.smartpoultry.model.ResponseData
import com.domainbangla.smartpoultry.networking.ApiService


class MainActivity : AppCompatActivity(), SPListener {

    val TAG = "MainActivity"
    lateinit var tvTemp: TextView
    lateinit var tvUpdateInfo: TextView
    lateinit var tvStatus: TextView
    lateinit var ivRefresh: ImageView
    lateinit var ivFan: ImageView

    var isRunning = false

    val updateHandler = Handler(Looper.getMainLooper())

    companion object {
        var minTemp = 0
        var maxTemp = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()

        callNetwork()
        runTimer()
    }

    private fun runTimer() {
        updateHandler.post(object : Runnable {
            override fun run() {
                if (isRunning){
                    callNetwork()
                    Log.e(TAG,"Network is called")
                }
                updateHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onStop() {
        super.onStop()
        isRunning = false
    }

    private fun initialize() {
        tvTemp = findViewById(R.id.tvTemp)
        tvUpdateInfo = findViewById(R.id.tvUpdateInfo)
        tvStatus = findViewById(R.id.tvStatus)
        ivRefresh = findViewById(R.id.ivRefresh)
        ivFan = findViewById(R.id.ivFan)
        ivRefresh.setOnClickListener {
            callNetwork()
        }
    }

    private fun callNetwork() {
        ApiService.getResponse(this@MainActivity, this)
    }

    fun rotateFan(on: Boolean) {
        if (on) {
            val rotate = RotateAnimation(
                0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 100
            rotate.repeatCount = Animation.INFINITE
            ivFan.startAnimation(rotate)
        } else {
            ivFan.clearAnimation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.optConfigure -> {
                val intent = Intent(this, ConfigureActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResponse(responseData: ResponseData) {
        minTemp = responseData.minTemp
        maxTemp = responseData.maxTemp
        tvTemp.setText("${responseData.currenTemp} \u2103")
        tvUpdateInfo.setText("Last updated on: ${responseData.updatedAt}")
        if (responseData.fanStatus == 1) {
            tvStatus.setText("On")
            rotateFan(true)
        } else {
            tvStatus.setText("Off")
            rotateFan(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}