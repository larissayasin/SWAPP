package com.app.lyasin.starwarsapp

import io.realm.RealmList
import io.realm.RealmObject

open class Character(var name: String = "", var films: RealmList<Film> = RealmList(), var url :  String = "", var time: String = "") : RealmObject(){

}