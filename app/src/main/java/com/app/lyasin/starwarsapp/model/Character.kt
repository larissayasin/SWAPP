package com.app.lyasin.starwarsapp.model

import io.realm.RealmList
import io.realm.RealmObject

open class Character(var name: String = "",
                     var films: RealmList<String> = RealmList(),
                     var filmsDetails : RealmList<Film> = RealmList(),
                     var url :  String = "",
                     var time: String = "") : RealmObject(){}