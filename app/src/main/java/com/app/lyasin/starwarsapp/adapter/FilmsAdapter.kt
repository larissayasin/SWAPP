package com.app.lyasin.starwarsapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lyasin.starwarsapp.R
import com.app.lyasin.starwarsapp.model.Film
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_film.view.*

class FilmsAdapter(private val films: ArrayList<Film>,
                   private val context: Context) : Adapter<FilmsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val note = films[position]
        holder?.let {
            it.bindView(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_character, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun addItem(film : Film){
        films.add(film)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(film: Film) {
            itemView.tv_film_name.text = film.title
            itemView.tv_film_url.text = film.url
            Glide.with(itemView).load(film.Poster).into(itemView.iv_poster)
        }

    }

}