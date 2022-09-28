package com.erhansen.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity //Room database annotations
data class Model(

    @ColumnInfo(name = "name") //Veritabanında oluşturulacak olan sütun isimlerini burada verebiliyoruz.
    @SerializedName("name")
    val countryName: String?,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String?,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String?,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String?,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String?,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageURL: String?) {

    @PrimaryKey(autoGenerate = true) //otomatik id oluşturacak.
    var uuid: Int = 0

}