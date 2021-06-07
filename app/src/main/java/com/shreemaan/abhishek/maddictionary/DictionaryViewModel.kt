package com.shreemaan.abhishek.maddictionary

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreemaan.abhishek.maddictionary.network.DictionaryApi
import com.shreemaan.abhishek.maddictionary.network.Result
import kotlinx.coroutines.launch

const val ERROR_CODE_404 = "retrofit2.HttpException: HTTP 404 "

class DictionaryViewModel : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: MutableLiveData<Int>
        get() = _progressBarVisibility

    private val _response = MutableLiveData<Result>()
    val response: LiveData<Result>
        get() = _response

//    we will increment this value so that observer gets notified in the DictionaryActivity and
//    pops up a toast that says, "Meanings for the given word not found"

    private val _forShowingError404Toast = MutableLiveData<Int>()
    val forShowingError404Toast: LiveData<Int>
        get() = _forShowingError404Toast

//    this variable will be used to supply values to the variable "_forShowingError404Toast"
//    see line number 50
//    following this approach because we cannot change the value of "_forShowingError404Toast" because
//    it is null initially. So we assign the value of "valueSupplier" to "_forShowingError404Toast"
//    and then increment "valueSupplier" so the next time error occurs, the value of
//    "_forShowingError404Toast" changes and the observer gets notified

    var valueSupplier: Int = 0

    fun getDictionaryMeaning(query: String) {
        _progressBarVisibility.value = View.VISIBLE
        viewModelScope.launch {
            try {
                val response = DictionaryApi.retrofitService.getMeaning(query)
                val result: Result = response?.get(0)
                _progressBarVisibility.value = View.GONE
                _response.value = result
            } catch (e: Exception){
                _progressBarVisibility.value = View.GONE
                if(e.toString() == ERROR_CODE_404){
                    _forShowingError404Toast.value = valueSupplier++
                }
            }
        }
    }
}