package com.stalker.utility

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var flightMode: Button
    lateinit var power: Button
    lateinit var setting: Button
    lateinit var camera: Button

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewInitializer()

        flightMode.setOnClickListener { operateFlightMode() }

        power.setOnClickListener { operatePower() }

        setting.setOnClickListener { openSettingApp() }


        camera.setOnClickListener { openCameraApp() }


    }



    private fun viewInitializer() {

        flightMode = findViewById(R.id.FlightMode)
        power = findViewById(R.id.Power)
        setting = findViewById(R.id.SettingApp)

    }


    private fun operatePower() {
        val devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(this, DeviceAdminReceiver::class.java)

        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.lockNow()
        } else {

            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "This app requires device administrator privileges to lock the screen."
            )
            startActivity(intent)
        }
    }

    private fun operateFlightMode() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        startActivity(intent)
    }


    private fun openSettingApp() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }


    private fun openCameraApp() {
        TODO("Not yet implemented")
    }




}

