package com.app.lyasin.starwarsapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Film : RealmObject(){
    @PrimaryKey
    var url : String = ""
    var title: String = ""
    var episode_id: Int = 0
    var Poster : String = ""
}