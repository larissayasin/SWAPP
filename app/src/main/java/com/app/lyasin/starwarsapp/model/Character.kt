package com.app.lyasin.starwarsapp.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Character : RealmObject(){
    @PrimaryKey
    var url :  String = ""
    var name: String = ""
    var films: RealmList<String> = RealmList()
    var filmsDetails : RealmList<Film> = RealmList()
    var time: String = ""
    var birth_year : String = ""
}