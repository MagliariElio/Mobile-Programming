package it.massimoregoli.codicefiscale.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountryViewModel : ViewModel() {
    var listOfCountries : MutableLiveData<List<CountryModel>> = MutableLiveData()

    fun updateList(newList : List<CountryModel>) {
        listOfCountries.value = newList
    }
}