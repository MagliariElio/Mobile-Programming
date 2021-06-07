package it.mem.myapplicationmarvel.ui.favorite

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.mem.myapplicationmarvel.R
import it.mem.myapplicationmarvel.activities.CharacterDetailsActivity
import it.mem.myapplicationmarvel.data.local.FavoriteCharacterViewModel
import it.mem.myapplicationmarvel.databinding.FavoriteCharactersListBinding


class FavoriteCharacterListFragment : Fragment() {

    private lateinit var mFavoriteCharacterViewModel: FavoriteCharacterViewModel

    private lateinit var binding: FavoriteCharactersListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FavoriteCharactersListBinding.inflate(layoutInflater, container, false)

        //Recycler
        val adapter= FavoriteCharacterListAdapter()
        val recyclerView=binding.recyclerCharacters
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        //characterViewModel
        mFavoriteCharacterViewModel=ViewModelProvider(this).get(FavoriteCharacterViewModel::class.java)
        mFavoriteCharacterViewModel.readAllData.observe(viewLifecycleOwner, { favoriteCharacter->
            adapter.setData(favoriteCharacter)
        })

        adapter.onItemClick={
            Log.v("Marvel", it.id.toString())

            val intent= Intent(activity, CharacterDetailsActivity::class.java)
            intent.putExtra("characterId", it.id)
            activity?.startActivity(intent)
        }

        val fab: FloatingActionButton = binding.fab
        fab.imageTintList= ColorStateList.valueOf((Color.parseColor("#FFFF12")))


        fab.setOnClickListener {
            fab.imageTintList= ColorStateList.valueOf((Color.parseColor("#FFFFFF")))

            //childFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, FavoriteCharacterListFragment()).commit()
//            val fragmentManager=parentFragmentManager
//            fragmentManager.beginTransaction().hide(this)
//            fragmentManager.beginTransaction().add(R.id.nav_host_fragment, FavoriteCharacterListFragment())
//            fragmentManager.beginTransaction().commit()

            findNavController().navigate(R.id.action_favoriteCharacterListFragment_to_nav_characters)

        }


        return binding.root
    }


}