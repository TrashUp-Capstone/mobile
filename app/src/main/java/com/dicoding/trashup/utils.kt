package com.dicoding.trashup

import android.content.Context
import android.net.Uri
import com.dicoding.trashup.utils.createCustomTempFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(createdAt: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(createdAt)
    return outputFormat.format(date?:"")
}

fun uriToFile(imageUri: Uri?, context: Context): File {
    val myFile = createCustomTempFile(context) // membuat sebuah File sementara (temporary)
    val inputStream = imageUri?.let { context.contentResolver.openInputStream(it) } as InputStream // nputStream ini akan digunakan untuk membaca data dari Uri gambar
    // Selama masih ada data yang dapat dibaca dari InputStream, maka data tersebut akan dibaca ke dalam buffer.
    val outputStream = FileOutputStream(myFile) //  data tersebut diproses dan disimpan di dalam OutputStream yang menghasilkan bentuk File.
    val buffer = ByteArray(1024) // Buffer adalah sebuah tempat penyimpanan sementara yang digunakan untuk membaca dan menulis data dalam potongan-potongan kecil, bukan sekaligus
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0 , length)
    outputStream.close()
    inputStream.close()
    return myFile
}


fun formatDateProfile(dateString: String?): String {
    return if (dateString != null) {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Unknown date"
            }
        } catch (e: Exception) {
            "Invalid date format"
        }
    } else {
        "No date available"
    }
}