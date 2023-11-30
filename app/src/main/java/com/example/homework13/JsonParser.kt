package com.example.homework13

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class JsonParser(private val context: Context) {

    data class ParsedFormData(
        val formData: List<List<FormField>>?,
        val numberOfArrays: Int
    )

    fun parseFormData(fileName: String): ParsedFormData {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)

            val listType = object : TypeToken<List<List<FormField>>>() {}.type
            val formData = Gson().fromJson<List<List<FormField>>>(json, listType)

            ParsedFormData(formData, formData?.size ?: 0)

        } catch (e: IOException) {
            e.printStackTrace()
            ParsedFormData(null, 0)
        }
    }
}

