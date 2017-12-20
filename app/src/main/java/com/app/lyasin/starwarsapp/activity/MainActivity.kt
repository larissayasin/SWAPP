package com.app.lyasin.starwarsapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.app.lyasin.starwarsapp.R
import com.app.lyasin.starwarsapp.adapter.CharactersAdapter
import com.app.lyasin.starwarsapp.model.Character
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()
    var charactersList : ArrayList<Character> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_qr_code.setOnClickListener { startActivity<QrCodeActivity>() }
    }

    override fun onResume() {
        super.onResume()
        getAllCharacters()
    }

    private fun getAllCharacters(){
        charactersList.addAll(realm.where(Character::class.java).findAll())
        updateView()
    }

    private fun updateView(){
        rv_characters.adapter = CharactersAdapter(charactersList, this)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_characters.layoutManager = llm
    }
}
