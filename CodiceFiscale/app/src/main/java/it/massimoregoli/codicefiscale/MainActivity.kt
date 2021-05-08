package it.massimoregoli.codicefiscale

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import it.massimoregoli.codicefiscale.databinding.ActivityMainBinding
import androidx.lifecycle.Observer
import it.massimoregoli.codicefiscale.adapters.MyArrayAdapter
import it.massimoregoli.codicefiscale.viewmodels.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "CF2021"
        private const val FILENAME = "countrycode.csv"
    }

    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)

    private lateinit var binding: ActivityMainBinding
    private var fiscalCode = CFModel()
    private var cFviewModel = CFViewModel()
    private val countryList = mutableListOf<CountryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        setUIController()
    }

    private fun setUIController() {
        binding.etLastName.doAfterTextChanged { name ->
            fiscalCode.lastName = name.toString()
            cFviewModel.getFiscalCode(fiscalCode)
        }
        binding.etFirstName.doAfterTextChanged { name ->
            fiscalCode.firstName = name.toString()
            cFviewModel.getFiscalCode(fiscalCode)
        }
        binding.etBirthdate.doAfterTextChanged { name ->
            fiscalCode.birthDate = name.toString()
            cFviewModel.getFiscalCode(fiscalCode)
        }

        binding.etCountryName.setOnItemClickListener { adapterView, _, i,_ ->
            fiscalCode.birthPlace =  (adapterView.adapter.getItem(i) as CountryModel).code
            cFviewModel.getFiscalCode(fiscalCode)
        }
        binding.etCountryName.doAfterTextChanged { name ->
            findSingleCountry(name.toString())
        }

        binding.cpFemale?.setOnCheckedChangeListener { _ , _->
            if(binding.cpFemale!!.isChecked)
                fiscalCode.gender = "F"
            else
                fiscalCode.gender = "M"
            cFviewModel.getFiscalCode(fiscalCode)
        }

        binding.etBirthdate.setOnClickListener {
            val view = this.currentFocus
            if(view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

            val getDate: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this, R.style.datePicker, { _, i, i2, i3 ->

                    val selectDate: Calendar = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR, i)
                    selectDate.set(Calendar.MONTH, i2)
                    selectDate.set(Calendar.DAY_OF_MONTH, i3)
                    val date: String = formatDate.format(selectDate.time)
                    if (Calendar.getInstance().after(selectDate))
                        binding.etBirthdate.setText(date)
                    else
                        Toast.makeText(this, "Enter a date earlier than the current date", Toast.LENGTH_SHORT).show()
                },
                getDate.get(Calendar.YEAR),
                getDate.get(Calendar.MONTH),
                getDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }



        readFile()
    }

    private fun findSingleCountry(name: String) {
        CoroutineScope(Dispatchers.Default).launch {
            fiscalCode.birthPlace = "X999"
            for (country in countryList) {
                if (country.description.equals(name, ignoreCase = true)) {
                    fiscalCode.birthPlace = country.code
                    break
                }
            }
            cFviewModel.getFiscalCode(fiscalCode)
        }
    }

    private fun readFile() {
        binding.etCountryName.isEnabled = false
        binding.etCountryName.hint = "Wait Please"
        val reader = assets.open(FILENAME)
            .bufferedReader()
        CoroutineScope(Dispatchers.IO).launch {
            reader.useLines { lines ->
                lines.forEach {
                    val fields = it.toUpperCase(Locale.getDefault()).split(";")
                    countryList.add(CountryModel(fields[0], fields[1], fields[2]))
                }
                launch(Dispatchers.Default) {
                    countryList.sortBy { it.description }
                    launch(Dispatchers.Main) {
                        Log.w(TAG, "LETTO!")
                        binding.etCountryName.isEnabled = true
                        val adapter = MyArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            countryList
                        )
                        binding.etCountryName.setAdapter(adapter)
                        binding.etCountryName.hint = "Birthplace"
                    }
                }
            }
        }
    }

    private fun setViewModel() {
        val fiscalCodeObserver = Observer<String> { cf ->
            Log.w(TAG, cf)
            binding.tvFiscalCode.text = cf
            binding.tvFiscalCode.isVisible = true
        }
        cFviewModel.fiscalCode.observe(this, fiscalCodeObserver)

//        val countryObserver = Observer<List<CountryModel>> {c ->
//            binding.etCountryName.setAdapter(ArrayAdapter<CountryModel>(this,
//                android.R.layout.simple_dropdown_item_1line, c))
//        }
//        countryViewModel.listOfCountries.observe(this, countryObserver)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("lastName", binding.etLastName.text.toString())
        savedInstanceState.putString("firstName", binding.etFirstName.text.toString())
        savedInstanceState.putString("birthDate", binding.etBirthdate.text.toString())
        savedInstanceState.putString("countryName", binding.etCountryName.text.toString())
        savedInstanceState.putString("fiscalCode", binding.tvFiscalCode.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.etLastName.setText(savedInstanceState.getString("lastName"))
        binding.etFirstName.setText(savedInstanceState.getString("firstName"))
        binding.etBirthdate.setText(savedInstanceState.getString("birthDate"))
        binding.etCountryName.setText(savedInstanceState.getString("countryName"))
        binding.tvFiscalCode.text = savedInstanceState.getString("fiscalCode")
    }
}