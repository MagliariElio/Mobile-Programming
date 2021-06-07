package it.mem.myapplicationmarvel.ui.characters

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import it.mem.myapplicationmarvel.R
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


        setUI()

        val fab: FloatingActionButton = binding.fab
        fab.imageTintList= ColorStateList.valueOf((Color.parseColor("#FFFFFF")))


        fab.setOnClickListener {
            fab.imageTintList= ColorStateList.valueOf((Color.parseColor("#FFFF12")))

            //childFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, FavoriteCharacterListFragment()).commit()
//            val fragmentManager=parentFragmentManager
//            fragmentManager.beginTransaction().hide(this)
//            fragmentManager.beginTransaction().add(R.id.nav_host_fragment, FavoriteCharacterListFragment())
//            fragmentManager.beginTransaction().commit()

            findNavController().navigate(R.id.action_nav_characters_to_favoriteCharacterListFragment)

        }


        return binding.root
    }

    private fun setUI() {
        val llm = LinearLayoutManager(activity)
        binding.recyclerCharacters.layoutManager = llm
        binding.recyclerCharacters.adapter = adapter
        subscribeToList("")
        adapter.onItemClick={
            Log.v("Marvel", it.id.toString())

            val intent= Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra("characterId", it.id)
            activity?.startActivity(intent)
        }

        val searchView=binding.searchBar


        searchView.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subscribeToList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                subscribeToList(s.toString())
            }
        })


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", binding.recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    override fun onResume() {
        super.onResume()
        setUI()
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

