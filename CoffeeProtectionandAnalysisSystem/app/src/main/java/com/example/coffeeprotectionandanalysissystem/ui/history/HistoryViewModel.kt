package com.example.coffeeprotectionandanalysissystem.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeprotectionandanalysissystem.database.History

class HistoryViewModel : ViewModel() {
    private val _historyList = MutableLiveData<List<History>>()
    val historyList: LiveData<List<History>> get() = _historyList

    fun setHistoryList(historyList: List<History>) {
        _historyList.postValue(historyList)
    }
}
