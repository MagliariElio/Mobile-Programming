package it.mem.myapplicationmarvel.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import it.mem.myapplicationmarvel.R
import it.mem.myapplicationmarvel.data.local.FavoriteCharacters
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDao
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDatabase
import it.mem.myapplicationmarvel.databinding.ActivityCharacterDetailsBinding
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.paging.characters.characterdetails.*
import it.mem.myapplicationmarvel.extensions.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailsBinding


    private var userDb: FavoriteCharactersDatabase? = FavoriteCharactersDatabase.INSTANCE
    private var characterDao: FavoriteCharactersDao?=userDb?.FavoriteCharactersDao()

    private val viewModelComics: ComicsByCharactersViewModel by lazy {
        ViewModelProvider(this).get(ComicsByCharactersViewModel::class.java)
    }
    
    private val viewModelSeries: SeriesByCharactersViewModel by lazy {
        ViewModelProvider(this).get(SeriesByCharactersViewModel::class.java)
    }

    private val viewModelStories: StoriesByCharactersViewModel by lazy {
        ViewModelProvider(this).get(StoriesByCharactersViewModel::class.java)
    }

    private val adapterComics: ComicsByCharactersAdapter by lazy {
        ComicsByCharactersAdapter()
    }

    private val adapterSeries: SeriesByCharactersAdapter by lazy {
        SeriesByCharactersAdapter()
    }

    private val adapterStories: StoriesByCharactersAdapter by lazy {
        StoriesByCharactersAdapter()
    }

    private var recyclerStateComics: Parcelable? = null

    private var recyclerStateSeries: Parcelable? = null

    private var recyclerStateStories: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        val id = extras?.getInt("characterId", 0)
        Log.v("MarvelCharId", id.toString())

        var favoriteCharacter: FavoriteCharacters?=null

        if (id != null) {
            CoroutineScope(Dispatchers.IO).launch {

                val responseCharacters = MarvelAPI.getService().searchCaracterById(id)
                launch(Dispatchers.Main) {
                    val character = responseCharacters.data.results[0]

                    binding.ivCharacter.load("${character.thumbnail.path}.${character.thumbnail.extension}")

                    binding.txtDescription.text = character.description
                    binding.txtName.text = character.name
                    binding.txtId.text = character.id.toString()
                    Log.v("MarvelUrl", "${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
                    var llm = LinearLayoutManager(this@CharacterDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerComics.layoutManager = llm
                    binding.recyclerComics.adapter = adapterComics
                    subscribeToComicsList(character.id)

                    llm = LinearLayoutManager(this@CharacterDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerSeries.layoutManager = llm
                    binding.recyclerSeries.adapter = adapterSeries
                    subscribeToSeriesList(character.id)

                    llm = LinearLayoutManager(this@CharacterDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerStories.layoutManager = llm
                    binding.recyclerStories.adapter = adapterStories
                    subscribeToStoriesList(character.id)
                    val builder=SpannableStringBuilder()
                    character.urls.forEach {
                        builder.append(
                                " "+it.type+": "+it.url+"\n\n",
                                BulletSpan(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    binding.txtUrlsList.text = builder
                    binding.txtUrlsList.linksClickable=true




                    favoriteCharacter = FavoriteCharacters(character.id, character.name, "${character.thumbnail.path}.${character.thumbnail.extension}")

                }

            }

            binding.txtIdTitle.text = getString(R.string.marvel_Id)
            binding.txtComics.text = getString(R.string.comics)
            binding.txtSeries.text=getString(R.string.series_string)
            binding.txtStories.text=getString(R.string.stories_string)
            binding.txtUrls.text=getString(R.string.string_urls)



            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = checkCharacter(id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            isChecked = true
                            Log.v("Marvel", " $id count $count")
                        } else {
                            isChecked = false
                            Log.v("Marvel", "count $count")
                        }
                    }


                }

                binding.ckFavorite.isChecked = isChecked
            }




            binding.ckFavorite.setOnClickListener {

                if (favoriteCharacter!=null) {
                    isChecked = !isChecked
                    if (isChecked) {
                        favoriteCharacter?.name?.let { it1 -> favoriteCharacter?.id?.let { it2 -> favoriteCharacter?.image?.let { it3 -> addToFavorite(it1, it2, it3) } } }
                    } else {
                        favoriteCharacter?.id?.let { it1 -> favoriteCharacter?.name?.let { it2 -> favoriteCharacter?.image?.let { it3 -> removeFromFavorite(it1, it2, it3) } } }
                    }
                }

            }


        }
    }

    private fun addToFavorite(name:String, id:Int, image:String){
        CoroutineScope(Dispatchers.IO).launch {
            val character = FavoriteCharacters(
                    id,
                    name,
                    image
            )
            characterDao?.addCharacter(character)
        }
        Log.w("Marvel", "$name, $id aggiunto")
    }

    private fun checkCharacter(id:Int)= characterDao?.checkCharacter(id)

    private fun removeFromFavorite(id: Int, name: String, image:String){
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteCharacter= FavoriteCharacters(id, name, image)
            characterDao?.removeFromFavorite(favoriteCharacter)
        }
        Log.w("Marvel", "$name, $id rimosso")

    }






    @SuppressLint("CheckResult")
    private fun subscribeToComicsList(id:Int) {
        viewModelComics.getComicsList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list ->
                            Log.v("NGVL", "Entrat0")
                            adapterComics.submitList(list)
                            if (recyclerStateComics != null) {
                                binding.recyclerComics.layoutManager?.onRestoreInstanceState(recyclerStateComics)
                                recyclerStateComics = null
                            }
                        },
                        { e ->
                            Log.e("NGVL", "Error", e)
                        }
                )
    }

    @SuppressLint("CheckResult")
    private fun subscribeToSeriesList(id:Int) {
        viewModelSeries.getSeriesList(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.v("NGVL", "Entrat0")
                    adapterSeries.submitList(list)
                    if (recyclerStateSeries != null) {
                        binding.recyclerSeries.layoutManager?.onRestoreInstanceState(recyclerStateSeries)
                        recyclerStateSeries = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                })
    }


    @SuppressLint("CheckResult")
    private fun subscribeToStoriesList(id:Int) {
        viewModelStories.getStoriesList(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.v("NGVL", "Entrat0")
                    adapterStories.submitList(list)
                    if (recyclerStateStories != null) {
                        binding.recyclerStories.layoutManager?.onRestoreInstanceState(recyclerStateStories)
                        recyclerStateStories = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                })
    }



}