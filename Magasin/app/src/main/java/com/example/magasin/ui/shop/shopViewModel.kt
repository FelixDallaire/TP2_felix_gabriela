package com.example.magasin.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class shopViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is shop Fragment"
    }
    val text: LiveData<String> = _text
}