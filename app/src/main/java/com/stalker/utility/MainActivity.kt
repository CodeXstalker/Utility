package com.stalker.utility

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch


class MainActivity : AppCompatActivity() {
    lateinit var flightMode : Button
    lateinit var power : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewInitializer()

        flightMode.setOnClickListener { operateFlightMode() }

        power.setOnClickListener { operatePower() }
    }

    private fun operatePower() {
//        val devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        val componentName = ComponentName(this, DeviceAdminReceiver::class.java)
//
//        if (devicePolicyManager.isAdminActive(componentName)) {
//            devicePolicyManager.lockNow()
//        } else {
//            // The app is not a device administrator. Request for admin privileges.
//            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
//            intent.putExtra(
//                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//                "This app requires device administrator privileges to lock the screen."
//            )
//            startActivity(intent)
//        }
    }

    private fun operateFlightMode() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        startActivity(intent)
    }

    private fun viewInitializer() {

        flightMode = findViewById(R.id.FlightMode)
        power = findViewById(R.id.Power)

    }

}