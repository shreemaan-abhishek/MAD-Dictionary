@file:Suppress("DEPRECATION")

package com.shreemaan.abhishek.maddictionary

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shreemaan.abhishek.maddictionary.adapters.MeaningsAdapter
import com.shreemaan.abhishek.maddictionary.databinding.ActivityDictionaryBinding

class DictionaryActivity : AppCompatActivity() {
    private lateinit var dictionaryViewModel: DictionaryViewModel
    private lateinit var binding: ActivityDictionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDictionaryBinding.inflate(layoutInflater)
        val root: View = binding.root
        setContentView(root)
        dictionaryViewModel =
            ViewModelProvider(this).get(DictionaryViewModel::class.java)

        val tvWord: TextView = binding.word
        val tvPhonetic: TextView = binding.phonetics
        val rvMeanings: RecyclerView = binding.rvDefinitions

        dictionaryViewModel.response.observe(this, {
            tvWord.text = it.word

            // handling the cases where the api does not return any IPA notation
            try {
                tvPhonetic.text = it.phonetics[0].text
            } catch (e: Exception) {
                tvPhonetic.text = "IPA not available" // IPA -> International Phonetic Alphabet
            }
            rvMeanings.layoutManager = LinearLayoutManager(
                this,
                RecyclerView.VERTICAL, false
            )
            // providing data to the child RecyclerView in the nested RecyclerView
            // see: https://medium.com/android-news/easily-adding-nested-recycler-view-in-android-a7e9f7f04047
            rvMeanings.adapter = MeaningsAdapter(it.meanings)
        })

        dictionaryViewModel.forShowingError404Toast.observe(this, {
            Toast.makeText(this, "Meanings for the given word not found", Toast.LENGTH_LONG).show()
        })

        dictionaryViewModel.progressBarVisibility.observe(this, {
            binding.progressBar.visibility = it
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isConnectedToNetwork()) {
                    query?.let { dictionaryViewModel.getDictionaryMeaning(it) }
                } else {
                    Toast.makeText(baseContext,"No internet connection", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

//        displaying the meaning of dictionary on app startup
        if (isConnectedToNetwork()) {
            dictionaryViewModel.getDictionaryMeaning("dictionary")
        } else {
            Toast.makeText(this,"No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun isConnectedToNetwork(): Boolean {
        var isConnected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isConnected = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    isConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isConnected
    }
}