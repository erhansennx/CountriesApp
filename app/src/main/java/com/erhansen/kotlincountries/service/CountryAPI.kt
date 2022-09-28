package com.erhansen.kotlincountries.service

import com.erhansen.kotlincountries.model.Model
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    // https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    // BASEURL: https://raw.githubusercontent.com/
    // EXTENSION URL: atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Model>> //Single Call'u: Bir veriyi bir defa, garanti bir şekilde alır.




}