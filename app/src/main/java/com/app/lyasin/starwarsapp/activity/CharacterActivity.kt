package com.app.lyasin.starwarsapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.app.lyasin.starwarsapp.R
import com.app.lyasin.starwarsapp.adapter.FilmsAdapter
import com.app.lyasin.starwarsapp.factory.RetrofitFactory
import com.app.lyasin.starwarsapp.model.Character
import com.app.lyasin.starwarsapp.model.Film
import com.app.lyasin.starwarsapp.service.SWService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_character.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import retrofit2.Retrofit
import java.util.*

class CharacterActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()
    var filmsList : ArrayList<Film> = ArrayList()
    var adapter : FilmsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        val intent = intent

        if(intent.extras[getString(R.string.from_db)] != null){
            searchCharacterDB(intent.getStringExtra(getString(R.string.from_db)))
        }
        else if (intent.extras[getString(R.string.from_qrcode)] != null) {
            searchCharacter(intent.getStringExtra(getString(R.string.from_qrcode)))
        }
    }

    private fun searchCharacter(qrcodeUrl: String) {
        if (qrcodeUrl.contains(getString(R.string.swapi))) {
            val retrofit: Retrofit? = RetrofitFactory[qrcodeUrl]
            if (retrofit != null) {
                val dialog = indeterminateProgressDialog(message = "Loading character…", title = "Fetching data")
                dialog.show()
                val service = retrofit.create(SWService::class.java)

                val observable = service.getCharacter("")
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ character ->
                            character.time = Date().toString()
                            saveCharacter(character)
                            searchFilms(character)
                            //Log.d("TAG", character.name)
                            updateView(character)
                            dialog.cancel()
                        }, { error ->
                            dialog.cancel()
                            toast(getString(R.string.error_message))
                            //  Log.d("TAG", error.toString())
                        })
            }
        }
        else{
            toast(getString(R.string.error_message))
        }
    }

    private fun searchFilms(character: Character) {
        character.films
                .mapNotNull { RetrofitFactory[it] }
                .map { it.create(SWService::class.java) }
                .map { it.getFilm("") }
                .forEach {
                    it
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ film ->
                                character.filmsDetails.add(film)
                                saveCharacter(character)
                                adapter?.addItem(film)
                                searchPoster(film)
                            }, { error ->
                                toast(getString(R.string.error_message))
                                Log.d("TAG", error.toString())
                            })
                }
    }

    private fun searchPoster(film : Film){
        val retrofit: Retrofit? = RetrofitFactory[getString(R.string.omdbapi)]
        if (retrofit != null) {
            val service = retrofit.create(SWService::class.java)
            val observable = service.getPoster(getString(R.string.omdbtoken), film.title)
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ filmPoster ->
                        film.Poster = filmPoster.Poster
                        film.Website = filmPoster.Website
                        saveFilm(film)
                        adapter?.updateItem(film)
                    }, { error ->

                        toast(getString(R.string.error_message))
                        //Log.d("TAG", error.toString())
                    })
        }
    }

    private fun saveFilm(film : Film){
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(film)
        realm.commitTransaction()
    }

    private fun saveCharacter(character: Character) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(character)
        realm.commitTransaction()
    }

    private fun searchCharacterDB(url : String){
       val localCharacter =  realm.where(Character::class.java).equalTo("url", url).findFirst()
        filmsList = ArrayList(localCharacter?.filmsDetails)
        if (localCharacter != null) {
            updateView(localCharacter)
        }
    }

    private fun updateView(character: Character) {
        adapter = FilmsAdapter(filmsList, this)
        rv_films.adapter =  adapter
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_films.layoutManager = llm

        tv_character_details_name.text = String.format(getString(R.string.name), character.name)
        tv_character_details_url.text = String.format(getString(R.string.url), character.url)
        tv_character_details_birth.text = String.format(getString(R.string.birth), character.birth_year)
        tv_character_record_date.text = String.format(getString(R.string.recorded), character.time)
    }
}
