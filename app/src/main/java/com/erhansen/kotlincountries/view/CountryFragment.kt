package com.erhansen.kotlincountries.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erhansen.kotlincountries.R
import com.erhansen.kotlincountries.util.downloadFromUrl
import com.erhansen.kotlincountries.util.placeHolderProgressBar
import com.erhansen.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        countryViewModel.getDataFromRoom(countryUuid)

        observeLiveData()

    }

    private fun observeLiveData() {
        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                countryName.text = country.countryName
                countryRegion.text = country.countryRegion
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                context?.let {
                    countryImage.downloadFromUrl(country.imageURL, placeHolderProgressBar(it))
                }
            }
        })
    }


}