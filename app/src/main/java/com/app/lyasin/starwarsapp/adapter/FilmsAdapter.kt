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
import org.jetbrains.anko.browse

class FilmsAdapter(private val films: ArrayList<Film>,
                   private val context: Context) : Adapter<FilmsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val note = films[position]
        holder?.let {
            it.bindView(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_film, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun addItem(film : Film){
        films.add(film)
        this.notifyDataSetChanged()
    }

    fun updateItem(film : Film){
        val pos = getItemPosition(film)
        films[pos] = film
        this.notifyItemChanged(pos)
    }

    private fun getItemPosition(film:Film) : Int {
        return (0 until films.size).firstOrNull { films[it].url === film.url }
                ?: 0
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),  View.OnClickListener {

        fun bindView(film: Film) {
            itemView.tv_film_name.text = String.format(itemView.context.getString(R.string.title), film.title)
            itemView.tv_film_url.text = film.url
            itemView.tv_film_website.text = film.Website
            itemView.setOnClickListener(this)
            Glide.with(itemView)
                    .load(film.Poster)
                    .into(itemView.iv_poster)
        }

        override fun onClick(view: View?) {
            view?.context?.browse(view.tv_film_website.text as String)

        }


    }

}