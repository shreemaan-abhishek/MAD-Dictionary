package com.shreemaan.abhishek.maddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shreemaan.abhishek.maddictionary.R
import com.shreemaan.abhishek.maddictionary.network.Definition

// studying the JSON structure would be really helpful for understanding the adapter structure
// study it from the link given below. (Don't forget to beautify the JSON)
// https://api.dictionaryapi.dev/api/v2/entries/en_US/part
// JSON beautifier: https://jsonformatter.curiousconcept.com/

class DefinitionsAdapter(private val definitionsList: List<Definition>) :
    RecyclerView.Adapter<DefinitionsAdapter.DefinitionsViewHolder>() {

    class DefinitionsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvDefinition: TextView
        val tvPosition: TextView
        val tvSynonyms: TextView
        val tvSynonymsHeading: TextView
        val tvExample: TextView

        init {
            tvDefinition = v.findViewById(R.id.definition)
            tvSynonyms = v.findViewById(R.id.synonyms)
            tvSynonymsHeading = v.findViewById(R.id.tv_synonyms_heading)
            tvExample = v.findViewById(R.id.example)
            tvPosition = v.findViewById(R.id.tv_position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child_definitions, parent, false)
        return DefinitionsViewHolder(v)
    }

    override fun onBindViewHolder(holder: DefinitionsViewHolder, position: Int) {
        val nthDefinition: Definition = definitionsList[position]
        holder.apply {
            tvPosition.text = (position+1).toString() + ".  "
            tvDefinition.text = nthDefinition.definition
            // converting the list of synonyms to a string without square brackets
            val synonym = nthDefinition.synonyms.toString()
                .replace("[","").replace("]","")

            // if there are no synonyms, make the synonym TextView invisible
            if (synonym!="") {
                tvSynonyms.text = synonym
            } else {
                tvSynonyms.visibility =View.GONE
                tvSynonymsHeading.visibility = View.GONE
            }

            // if there are no examples, make the example synonym invisible
            val example = nthDefinition.example
            if (example!="") {
                tvExample.text = "\""+example+"\""
            } else {
                tvExample.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = definitionsList.size
}