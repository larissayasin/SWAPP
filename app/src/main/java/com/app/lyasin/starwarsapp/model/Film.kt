package com.app.lyasin.starwarsapp.model

import io.realm.RealmObject

/**
 * Created by larissayasin on 18/12/17.
 */
open class Film (var title: String = "", var episode_id: Int = 0, var poster : String = ""): RealmObject(){

}