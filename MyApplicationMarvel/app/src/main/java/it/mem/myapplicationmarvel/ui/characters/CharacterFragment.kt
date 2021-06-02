package it.mem.myapplicationmarvel.ui.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import it.mem.myapplicationmarvel.databinding.FragmentCharactersBinding

class CharacterFragment:Fragment() {
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
        subscribeToList()

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
    private fun subscribeToList() {
        viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
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

