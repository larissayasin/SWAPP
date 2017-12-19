package com.app.lyasin.starwarsapp.service

import com.app.lyasin.starwarsapp.model.Character
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url


interface CharacterService {
    @GET
    fun getCharacter(@Url url: String): Observable<Character>
}