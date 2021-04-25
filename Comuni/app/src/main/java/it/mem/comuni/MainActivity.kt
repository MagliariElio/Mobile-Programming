package it.mem.comuni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import it.mem.comuni.adapters.AdapterComuni
import it.mem.comuni.databinding.ActivityMainBinding
import it.mem.comuni.models.Comune
import it.mem.comuni.models.ListaComuni
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity() {

    companion object{
        private const val FILENAME = "listacomuni.txt"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdapterComuni
    private lateinit var listaComuni: ListaComuni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setUIController()

    }

    private fun setUIController() {
        val searchView=binding.searchBar

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.getFilter().filter(query)
                if (adapter.itemCount==0){
                    Toast.makeText(this@MainActivity, "Non ci sono comuni " +
                            "che iniziano con $query", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })



    }

    private fun loadData() {
        val reader = assets.open(FILENAME)
            .bufferedReader()
        CoroutineScope(Dispatchers.IO).launch{
            listaComuni = ListaComuni()
            reader.useLines { lines->
                lines.forEach {
                    listaComuni.add(Comune(it))
                }
                launch(Dispatchers.Default) {
                    listaComuni.listaComuni.sortBy { it.comune }
                    launch(Dispatchers.Main) {
                        adapter = AdapterComuni(this@MainActivity,listaComuni)
                        val layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.rvComuni.layoutManager = layoutManager
                        binding.rvComuni.adapter = adapter
                    }
                }

            }
        }
    }


}