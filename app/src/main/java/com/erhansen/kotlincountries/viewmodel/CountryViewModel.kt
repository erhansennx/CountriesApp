package com.erhansen.kotlincountries.viewmodel

import android.app.Application
import android.graphics.ColorSpace
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erhansen.kotlincountries.model.Model
import com.erhansen.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }

}