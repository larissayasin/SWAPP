package com.app.lyasin.starwarsapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.app.lyasin.starwarsapp.R
import com.app.lyasin.starwarsapp.factory.RetrofitFactory
import com.app.lyasin.starwarsapp.service.CharacterService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import retrofit2.Retrofit

class CharacterActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        val intent = intent
        if(intent.extras[getString(R.string.qrcode_text)] != null){
            searchCharacter(intent.getStringExtra(getString(R.string.qrcode_text)))
        }
    }

    fun searchCharacter(qrcodeUrl : String) {
//        if(qrcodeUrl.contains(getString(R.string.swapi))){
            var retrofit : Retrofit? = RetrofitFactory.get(getString(R.string.swapi))
            if(retrofit != null) {
                val service = retrofit.create(CharacterService::class.java)

                service.getCharacter(qrcodeUrl)
                val observable = service.getCharacter(qrcodeUrl)
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({character ->
                            Log.d("TAG", character.name)
                            
                        }, {error->
                            Log.d("TAG", error.toString())})
            }
//        }
    }
}
