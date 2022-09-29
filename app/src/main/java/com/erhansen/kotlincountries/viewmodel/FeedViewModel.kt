package com.erhansen.kotlincountries.viewmodel

import android.app.Application
import android.graphics.ColorSpace
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erhansen.kotlincountries.model.Model
import com.erhansen.kotlincountries.service.CountryAPIService
import com.erhansen.kotlincountries.service.CountryDatabase
import com.erhansen.kotlincountries.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    //Service Object
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable() //Kullan at objesi
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L //Nanosaniye cinsinden 10 dakikayı veriyor.
    //Live Data
    val countries = MutableLiveData<List<Model>>()
    //Error Live Data
    val countryError = MutableLiveData<Boolean>()
    //Live Data Loading
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData() {
        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }

    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(countryAPIService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Model>>(){

                override fun onSuccess(t: List<Model>) {
                    storeInSQLite(t)
                    Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()

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
    private fun showCountries(countryList: List<Model>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
    private fun storeInSQLite(countryList: List<Model>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*countryList.toTypedArray()) //Listedeki verileri tekil eleman yapmaya yarıyor. -İndividual-
            var i = 0
            while (i < listLong.size) {
                countryList[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(countryList)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}