package it.mem.codiceFiscale.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CFViewModel : ViewModel() {
    var fiscalCode = MutableLiveData<String>()

    fun getFiscalCode(model: CFModel) {
        val cf = model.encode()
        fiscalCode.postValue(cf)
    }
}