package com.app.lyasin.starwarsapp.service

import com.app.lyasin.starwarsapp.model.Character
import com.app.lyasin.starwarsapp.model.Film
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface SWService {
    @GET
    fun getCharacter(@Url url: String): Observable<Character>

    @GET
    fun getFilm(@Url url: String): Observable<Film>

    @GET("/")
    fun getPoster(
            @Query("apikey") token : String,
            @Query("t") title : String): Observable<Film>
}