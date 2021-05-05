package it.mem.codiceFiscale.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import it.mem.codiceFiscale.viewmodels.CountryModel
import java.util.*

class MyArrayAdapter(context: Context, val resource: Int, placesList: List<CountryModel>) :
    ArrayAdapter<CountryModel>(
        context,
        resource, placesList
    ) {
    private var allPlacesList = mutableListOf<CountryModel>()
    private var filteredPlacesList = mutableListOf<CountryModel>()

    init {
        allPlacesList = placesList as MutableList<CountryModel>
        filteredPlacesList = allPlacesList
    }

    override fun getCount(): Int {
        return filteredPlacesList.size
    }

    override fun getItem(p0: Int): CountryModel {
        return filteredPlacesList.get(p0)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context)
            .inflate(resource, parent, false) as TextView
        val text =
            "${filteredPlacesList[position].description} (${filteredPlacesList[position].province})"
        view.text = text
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredPlacesList = filterResults.values as MutableList<CountryModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toUpperCase(Locale.getDefault())

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    allPlacesList
                else
                    allPlacesList.filter {
                        it.description.toUpperCase(Locale.getDefault()).startsWith(queryString)
                    }
                return filterResults
            }
        }
    }


}