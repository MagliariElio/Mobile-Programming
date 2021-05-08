package it.mem.codiceFiscale.viewmodels

import android.content.Context
import kotlinx.coroutines.*
import java.util.*

class DBCountry {
    companion object {
        private const val FILENAME = "countrycode.csv"

        fun getCountries(context: Context, name: String): Deferred<List<CountryModel>> {
            return CoroutineScope(Dispatchers.IO).async {
                val ret = mutableListOf<CountryModel>()
                val reader = context.assets.open(FILENAME)
                    .bufferedReader()

                reader.useLines { lines ->
                    lines.forEach {
                        val fields = it.toUpperCase(Locale.getDefault()).split(";")
                        if (fields[0].startsWith(name.toUpperCase(Locale.getDefault())))
                            ret.add(CountryModel(fields[0], fields[1], fields[2]))
                    }
                }
                ret
            }
        }
    }


}