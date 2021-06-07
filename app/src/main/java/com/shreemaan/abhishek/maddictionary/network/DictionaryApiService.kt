package com.shreemaan.abhishek.maddictionary.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en_US/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface DictionaryApiService{
    @GET("{word}")
    suspend fun getMeaning(@Path("word") word: String): List<Result>
}
// exposing the retrofitService to the outer classes
object DictionaryApi{
    val retrofitService: DictionaryApiService by lazy { retrofit.create(DictionaryApiService::class.java) }
}