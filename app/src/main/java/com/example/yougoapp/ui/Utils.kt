package com.example.yougoapp.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun checkAdult(age: Int, result: Float): String {
        val category:String = when (age) {
            in 2..19 -> {
                getAdultCategory(result)
            }
            else -> {
                getChildCategory(result)
            }
        }
        return category
    }

    private fun getAdultCategory(result: Float): String {
        val category: String = if (result < 15) {
            "Severe Thinness"
        } else if (result in 15.0..16.0) {
            "Moderate Thinness"
        } else if (result > 16 && result <= 18.5) {
            "Mild Thinness"
        } else if (result > 18.5 && result <= 25) {
            "Normal"
        } else if (result > 25 && result <= 30) {
            "Overweight"
        } else if (result > 30 && result <= 35) {
            "Obese Class I"
        } else if (result > 35 && result <= 40) {
            "Obese Class II"
        } else {
            "Obese Class III"
        }
        return category
    }

    private fun getChildCategory(result: Float): String {
        val category: String = when {
            result < 15 -> {
                "Very severely underweight"
            }
            result in 15.0..16.0 -> {
                "Severely underweight"
            }
            result > 16 && result <= 18.5 -> {
                "Underweight"
            }
            result > 18.5 && result <= 25 -> {
                "Normal"
            }
            result > 25 && result <= 30 -> {
                "Overweight"
            }
            result > 30 && result <= 35 -> {
                "Moderately obese"
            }
            result > 35 && result <= 40 -> {
                "Severely obese"
            }
            else -> {
                "Very severely obese"
            }
        }
        return category
    }


}
private const val MAXIMAL_SIZE = 1000000 //1 MB
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun File.reduceFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height, matrix, true
    )
}