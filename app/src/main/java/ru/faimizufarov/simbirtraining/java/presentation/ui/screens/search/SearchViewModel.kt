package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _queryLiveData = MutableLiveData<String>()
    val queryLiveData: LiveData<String> = _queryLiveData

    fun setQuery(query: String) {
        _queryLiveData.value = query
    }
}
