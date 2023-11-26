package com.example.dalportal.ui.professorportal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfessorPortalViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Professor portal Fragment"
    }
    val text: LiveData<String> = _text
}