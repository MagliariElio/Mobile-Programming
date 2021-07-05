package it.mem.myapplicationmarvel.ui.characters

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
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

    private var order:String="name"
    private var name:String=""




//    private var recyclerState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)


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
        subscribeToList("", "name")
        adapter.onItemClick={
            Log.v("Marvel", it.id.toString())

            val intent= Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra("characterId", it.id)
            activity?.startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem =menu.findItem(R.id.menu_search)
        val searchView=searchItem.actionView as SearchView

        searchView.imeOptions=EditorInfo.IME_ACTION_DONE

        searchView.queryHint="Search"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                subscribeToList(query.toString(), order)
                name=query.toString()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subscribeToList(newText.toString(), order)
                name=newText.toString()
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        order= when(item.itemId){
            R.id.mnu_ordernameascending ->  "name"
            R.id.mnu_ordernamediscending-> "-name"
            R.id.mnu_ordermodifiedascending-> "modified"
            R.id.mnu_ordermodifieddiscending-> "-modified"
            else -> order
        }

        subscribeToList(name, order)
        return super.onOptionsItemSelected(item)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable("lmState", binding.recyclerCharacters.layoutManager?.onSaveInstanceState())
//        outState.putString("order", order)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        recyclerState = savedInstanceState?.getParcelable("lmState")
//        if(savedInstanceState?.getString("order")!=null){
//            order= savedInstanceState.getString("order").toString()
//        }
//    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    @SuppressLint("CheckResult")
    private fun subscribeToList(name:String, order:String= "name") {
        viewModel.getList(name, order)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { list ->
                        adapter.submitList(list)
//                        if (recyclerState != null) {
//                            binding.recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
//                            recyclerState = null
//                        }
                    },
                    { e ->
                        Log.e("Marvel", "Error", e)
                    }
            )
    }


}

