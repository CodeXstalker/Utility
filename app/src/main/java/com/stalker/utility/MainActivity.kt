package com.stalker.utility

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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

        power.setOnClickListener {
            operatePower()
            finish()
        }

        setting.setOnClickListener { openSettingApp() }


        camera.setOnClickListener { openCameraApp() }


    }


    private fun viewInitializer() {

        flightMode = findViewById(R.id.FlightMode)
        power = findViewById(R.id.Power)
        setting = findViewById(R.id.SettingApp)
        camera = findViewById(R.id.CameraApp)

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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val imageBitmap = it.extras?.get("data") as? Bitmap
                if (imageBitmap != null) {
                    saveImageToExternalStorage(imageBitmap)
                } else {
                    Toast.makeText(this, "Failed to get the image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveImageToExternalStorage(imageBitmap: Bitmap) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_$timeStamp.jpg"

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, imageFileName)

        try {
            val fos = FileOutputStream(imageFile)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            Toast.makeText(this, "Image saved to ${imageFile.absolutePath}", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

}

