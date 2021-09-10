package com.sarftec.lovequotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.application.repository.Repository
import com.sarftec.lovequotes.presentation.activity.BaseActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
    ) : BaseListViewModel() {

    private var inMemoryQuoteList: List<Quote> = emptyList()

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>>
        get() = _quotes


    override fun fetch() {
        viewModelScope.launch {
            baseBundle?.getInt(BaseActivity.CATEGORY_ID)?.let {
                inMemoryQuoteList = repository.fetchQuotes(it).shuffled()
                _quotes.value = inMemoryQuoteList
            }
        }
    }

    fun filter(query: String) {
        viewModelScope.launch {
            var result: List<Quote>? = null
            withContext(Dispatchers.Default) {
                result = if (query.isEmpty()) inMemoryQuoteList
                else inMemoryQuoteList.filter {
                    it.message.lowercase(Locale.ENGLISH).contains(
                        query.lowercase(Locale.ENGLISH)
                    )
                }
            }
            result?.let { _quotes.value = it }
        }
    }

    override fun getToolbarTitle(): StateFlow<String?> {
        val stateFlow = MutableStateFlow<String?>(null)
        baseBundle?.let {
            viewModelScope.launch {
                repository.getCategory(it.getInt(BaseActivity.CATEGORY_ID))?.let { category ->
                    stateFlow.value = category.category
                }
            }
        }
        return stateFlow
    }

    fun save(quote: Quote) {
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }
}