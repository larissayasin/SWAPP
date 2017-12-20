package com.app.lyasin.starwarsapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lyasin.starwarsapp.R
import com.app.lyasin.starwarsapp.activity.CharacterActivity
import com.app.lyasin.starwarsapp.model.Character
import kotlinx.android.synthetic.main.row_character.view.*
import org.jetbrains.anko.startActivity

class CharactersAdapter(private val characters: ArrayList<Character>,
                        private val context: Context) : Adapter<CharactersAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val note = characters[position]
        holder?.let {
            it.bindView(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_character, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView),  View.OnClickListener {

        fun bindView(character: Character) {
            itemView.tv_character_name.text = character.name
            itemView.tv_character_url.text = character.url
        }

        override fun onClick(view: View?) {
            view?.context?.startActivity<CharacterActivity>(view.context.getString(R.string.from_db) to view)
        }

    }

}