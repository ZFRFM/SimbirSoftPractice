package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    fun setQuery(query: String) {
        _query.value = query
    }
}
