package com.example.yougoapp.ui.detection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yougoapp.databinding.ActivityResultBinding
import com.example.yougoapp.ui.pose.DetailPoseActivity
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityResultBinding
    private var imageUri: Uri? = null
    companion object{
        const val  EXTRA_ID ="ID"
        const val  EXTRA_NAME = "name"
        const val IMAGE = " extraImage"
        const val  TITLE = "extraTitle"
        const val EXTRA_AKURASI = "akurasi"
        const val  EXTRA_ISCORRECT = "isCorrect"
        const val  EXTRA_IMAGE = "image"
    }
   private var id :String? =null
    private  var image: String? = null
    private  var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id   = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)
        image = intent.getStringExtra(IMAGE)
        title = intent.getStringExtra(TITLE)
        val akurasi = intent.getDoubleExtra(EXTRA_AKURASI,0.0)
        imageUri = intent?.getStringExtra(EXTRA_IMAGE)?.let { Uri.parse(it) }

        val cek = intent.getBooleanExtra(EXTRA_ISCORRECT,false)

        val decimalFormat = DecimalFormat("#.#")
        val formattedAkurasi = decimalFormat.format(akurasi)
        binding.akurasi.text = "$formattedAkurasi%"
        binding.name.text = name
        binding.ageTxt.text = cek.toString()
        imageUri?.let {
            binding.image.setImageURI(it)
        }



    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DetailPoseActivity::class.java)
        intent.putExtra(DetailPoseActivity.EXTRA_ID,id)
        intent.putExtra(DetailPoseActivity.EXTRA_TITLE,title)
        intent.putExtra(DetailPoseActivity.EXTRA_PHOTO,image)
        startActivity(intent)
        finish()
    }
}
