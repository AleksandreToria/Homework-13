package com.example.homework13

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _formData = MutableLiveData<List<List<FormField>>>()
    val formData: LiveData<List<List<FormField>>> get() = _formData

    fun loadJsonData(context: Context, fileName: String) {
        val jsonParser = JsonParser(context)
        val parsedData = jsonParser.parseFormData(fileName)

        val formDataList = parsedData.formData ?: emptyList()
        _formData.value = formDataList
    }
}


