package com.example.bungae.ui.post

import android.location.Address
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private var _coordinates = MutableLiveData<Address?>()
    val coordinates: LiveData<Address?>
        get() = _coordinates

    fun sendCoordinates(address: Address?) {
        _coordinates.value = address
    }
}