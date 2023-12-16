package com.example.yougoapp.ui.detection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.yougoapp.databinding.ActivityDetectionBinding
import com.example.yougoapp.ui.createCustomTempFile

class DetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private  var imageUri: Uri? = null
    private var idUser =""
    companion object {
        const val EXTRA_ID = "extraId"
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(EXTRA_ID)
        idUser = id ?:""
        if (!allPermissionGranted()){
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                CameraSelector.DEFAULT_BACK_CAMERA
            } else {
                CameraSelector.DEFAULT_FRONT_CAMERA
            }
            startCamera()
            id
        }
        binding.captureImage.setOnClickListener { takePhoto() }
        binding.gallery.setOnClickListener {
            startGallery()
        }
    }
    private  fun allPermissionGranted()= ContextCompat.checkSelfPermission(this, REQUIRED_PERMISSION)== PackageManager.PERMISSION_GRANTED

    private  fun startGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }
    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            val intent = Intent(this, StateActivity::class.java)
            intent.putExtra(StateActivity.EXTRA_IMAGE, imageUri.toString())
            intent.putExtra(StateActivity.ID_EXTRA,idUser)
            startActivity(intent)
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val prev = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)

                }
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, prev, imageCapture)
            } catch (e: Exception) {
                Toast.makeText(
                    this@DetectionActivity,
                    "gagal memunculkan Kamera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, outputFileResults.savedUri.toString())
                    setResult(CAMERAX_RESULT, intent)
                    launchStateActivity(outputFileResults.savedUri)

                    finish()


                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@DetectionActivity,
                        "gagal mengambil gambar",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        )
    }
    private  fun hideSystemUI(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        supportActionBar?.hide()
    }
    private  val orientationEventListener by lazy {
        object : OrientationEventListener(this){
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN){
                    return
                }
                val rotation = when(orientation){
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }
                imageCapture?.targetRotation = rotation
            }
        }
    }
    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }
    private fun launchStateActivity(savedUri: Uri?) {
        savedUri?.let { uri ->
            val intent = Intent(this, StateActivity::class.java)
            intent.putExtra(StateActivity.EXTRA_IMAGE, uri.toString())
            intent.putExtra(StateActivity.ID_EXTRA, idUser)
            startActivity(intent)
        } ?: run {

            Toast.makeText(this, "Error: Image URI is null", Toast.LENGTH_SHORT).show()
        }
    }

}