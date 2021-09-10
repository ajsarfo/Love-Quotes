package com.sarftec.lovequotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.application.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: Repository
) : BaseListViewModel() {

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>>
        get() = _quotes

    override fun getToolbarTitle(): StateFlow<String?> {
        return MutableStateFlow("Favorite Quotes")
    }

    override fun fetch() {
        viewModelScope.launch {
            _quotes.value = repository.fetchFavorites()
        }
    }

    fun save(quote: Quote) {
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }
}