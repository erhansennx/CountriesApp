package com.erhansen.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erhansen.kotlincountries.R
import com.erhansen.kotlincountries.adapter.CountryAdapter
import com.erhansen.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var feedViewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf()) //arrayListOf()'u vererek boş gönderdik.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModelProviders'ı kullanarak hangi fragment, activity, viewModel'in
        // sınıfı ile çalısmak istiyoruz bunu söyleyebiliriz.
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        feedViewModel.refreshData()

        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter

        observeLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.GONE
            feedViewModel.refreshData()
        }

    }

    fun observeLiveData() { //Live data'ları gözlemlemek

        feedViewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        feedViewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {   //hata var
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE
                 }
            }
        })

    }

}