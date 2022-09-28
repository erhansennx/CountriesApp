package com.erhansen.kotlincountries.viewmodel

import android.graphics.ColorSpace
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erhansen.kotlincountries.model.Model
import com.erhansen.kotlincountries.service.CountryAPIService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    //Service Object
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable() //Kullan at objesi
    //Live Data
    val countries = MutableLiveData<List<Model>>()
    //Error Live Data
    val countryError = MutableLiveData<Boolean>()
    //Live Data Loading
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(countryAPIService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Model>>(){

                override fun onSuccess(t: List<Model>) {
                    countries.value = t
                    countryError.value = false
                    countryLoading.value = false
                }

                override fun onError(e: Throwable) {
                    countryLoading.value = false
                    countryError.value = true
                    e.printStackTrace()
                }

            }))

        //subscribeOn() : Async olarak bu disposable işlemini yapma metodu
        //observeOn() :
    }




}