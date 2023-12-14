package com.enigma.discoverbatik.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Tools {
    private val timeStamp: String = SimpleDateFormat(Companion.FILENAME_FORMAT, Locale.US).format(Date())
    fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }
}