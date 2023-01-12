package com.example.graphqlsample.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apollographqlsample.CharactersListQuery
import com.example.graphqlsample.R
import com.example.graphqlsample.databinding.ItemCharacterBinding

//First, we need a DiffUtil class which is needed later for the ListAdapter.
// The DiffUtil and ListAdapter will boost the performance of the RecyclerView
//The Adapter for displaying data
class CharacterAdapter: ListAdapter<CharactersListQuery.Result, CharacterViewHolder>(CharacterDiffUtil()) {

    //onCreateViewHolder() only creates the CharacterViewHolder and returns it
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_character,
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    //onBindViewHolder() passes the an object of CharactersListQuery.Result to the data binding layout.
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.character = getItem(position)
    }

}

//ViewHolder for storing and caching data
//ViewHolder class which has as argument of ItemCharacterBinding and inherit from RecyclerView.ViewHolder
class CharacterViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)


class CharacterDiffUtil: DiffUtil.ItemCallback<CharactersListQuery.Result>(){
    override fun areItemsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersListQuery.Result,
        newItem: CharactersListQuery.Result
    ): Boolean {
        return oldItem == newItem
    }

}