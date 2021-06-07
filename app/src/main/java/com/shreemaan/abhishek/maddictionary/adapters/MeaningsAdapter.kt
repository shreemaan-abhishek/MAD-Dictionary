package com.shreemaan.abhishek.maddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shreemaan.abhishek.maddictionary.R
import com.shreemaan.abhishek.maddictionary.network.Meaning

// studying the JSON structure would be really helpful for understanding the adapter structure
// study it from the link given below. (Don't forget to beautify the JSON)
// https://api.dictionaryapi.dev/api/v2/entries/en_US/part
// JSON beautifier: https://jsonformatter.curiousconcept.com/

class MeaningsAdapter(private val meaningsList: List<Meaning>) :
    RecyclerView.Adapter<MeaningsAdapter.MeaningsViewHolder>() {

    class MeaningsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvPartOfSpeech: TextView
        val rvDefinitions: RecyclerView

        init {
            v.apply {
                tvPartOfSpeech = findViewById(R.id.part_of_speech)
                rvDefinitions = findViewById(R.id.rv_definitions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meanings, parent, false)
        return MeaningsViewHolder(v)
    }

    override fun onBindViewHolder(holder: MeaningsViewHolder, position: Int) {
        val nthMeaning = meaningsList[position]
        holder.tvPartOfSpeech.text = nthMeaning.partOfSpeech
        holder.rvDefinitions.adapter = DefinitionsAdapter(nthMeaning.definitions)
        holder.rvDefinitions.layoutManager = LinearLayoutManager(holder.tvPartOfSpeech.context,
            RecyclerView.VERTICAL, false)
    }

    override fun getItemCount() = meaningsList.size
}