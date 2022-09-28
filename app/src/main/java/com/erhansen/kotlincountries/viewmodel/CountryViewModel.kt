package com.erhansen.kotlincountries.viewmodel

import android.graphics.ColorSpace
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erhansen.kotlincountries.model.Model

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom() {
        val country = Model("Turkey","Ankara","Asia","TRY","Turkish","www.ss.com")
        countryLiveData.value = country
    }

}