package it.mem.myapplicationmarvel.ui.characters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import it.mem.myapplicationmarvel.activities.CharacterDetailsActivity
import it.mem.myapplicationmarvel.databinding.FragmentCharactersBinding

class CharacterFragment:Fragment(){
    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        binding= FragmentCharactersBinding.inflate(layoutInflater, container, false)

        val llm = LinearLayoutManager(activity)
        binding.recyclerCharacters.layoutManager = llm
        binding.recyclerCharacters.adapter = adapter
        subscribeToList("")

        adapter.onItemClick={
            Log.v("Marvel", it.id.toString())

            val intent= Intent(activity,CharacterDetailsActivity::class.java)
            intent.putExtra("characterId", it.id)
            activity?.startActivity(intent)
        }

        val searchView=binding.searchBar

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.v("NGVL", "Query")
                subscribeToList(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {

                subscribeToList(newText)
                return false
            }
        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", binding.recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    @SuppressLint("CheckResult")
    private fun subscribeToList(name:String) {
        viewModel.getList(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.v("NGVL", "Entrat0")
                    adapter.submitList(list)
                    if (recyclerState != null) {
                        binding.recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )
    }


}

