package com.example.homework13

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class JsonParser(private val context: Context) {

    fun parseFormData(fileName: String): List<List<FormField>>? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)

            val listType = object : TypeToken<List<List<FormField>>>() {}.type
            Gson().fromJson(json, listType)

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
