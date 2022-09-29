package com.erhansen.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erhansen.kotlincountries.model.Model

@Dao
interface CountryDao {

    @Insert //return Primary Keys (model -> uuid)
    suspend fun insertAll(vararg countries: Model) : List<Long>

    @Query("SELECT * FROM model") //return Countries
    suspend fun getAllCountries() : List<Model>

    @Query("SELECT * FROM model WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Model

    @Query("DELETE FROM model")
    suspend fun deleteAllCountries()

}