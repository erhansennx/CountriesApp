package com.erhansen.kotlincountries.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.erhansen.kotlincountries.R
import com.erhansen.kotlincountries.databinding.ItemCountryBinding
import com.erhansen.kotlincountries.model.Model
import com.erhansen.kotlincountries.util.downloadFromUrl
import com.erhansen.kotlincountries.util.placeHolderProgressBar
import com.erhansen.kotlincountries.view.FeedFragment
import com.erhansen.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(private val countryList: ArrayList<Model>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {

    class CountryViewHolder(var itemCountryBinding: ItemCountryBinding) : RecyclerView.ViewHolder(itemCountryBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country, parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        /*
        holder.itemView.name.text = countryList[position].countryName
        holder.itemView.region.text = countryList[position].countryRegion
        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.downloadFromUrl(countryList[position].imageURL, placeHolderProgressBar(holder.itemView.context))
        */

        holder.itemCountryBinding.country = countryList[position]
        holder.itemCountryBinding.listener = this
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    //Swipe Refresh
    fun updateCountryList(newCountryList: List<Model>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View) {
        val uuid = view.countryUUIDText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }


}