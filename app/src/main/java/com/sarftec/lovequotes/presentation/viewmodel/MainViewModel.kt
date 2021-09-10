package com.sarftec.lovequotes.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.application.model.Category
import com.sarftec.lovequotes.application.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
) : BaseListViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    override fun getToolbarTitle(): StateFlow<String?> {
        return MutableStateFlow<String?>(context.getString(R.string.app_name))
    }

    override fun fetch() {
        viewModelScope.launch {
            _categories.value = repository.fetchCategories()
        }
    }
}