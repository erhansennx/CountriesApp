package com.erhansen.kotlincountries.service

import com.erhansen.kotlincountries.model.Model
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    //  https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //  BASEURL: https://raw.githubusercontent.com/
    //  EXTENSION URL: atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json


    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val API = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getData() : Single<List<Model>> {
        return API.getCountries()
    }



}