package com.shreemaan.abhishek.maddictionary.network

// data class for converting the JSON to Kotlin Objects

// studying the JSON structure would be really helpful for understanding the adapter structure
// study it from the link given below. (Don't forget to beautify the JSON)
// https://api.dictionaryapi.dev/api/v2/entries/en_US/part
// JSON beautifier: https://jsonformatter.curiousconcept.com/

data class Definition(
    val definition: String,
    val synonyms: List<String> = arrayListOf(""),
    val example: String = ""
)

data class Meaning(
    val partOfSpeech: String = "",
    val definitions: List<Definition>
)

data class Phonetic(
    val text: String = "",
    val audio: String = ""
)

data class Result(
    val word: String,
    val phonetics: List<Phonetic> = arrayListOf(Phonetic()),
    val meanings: List<Meaning>
)