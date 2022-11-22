package com.example.mandatoryassignment.Models

import androidx.lifecycle.LiveData
import  androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.Repository.CatsRepository

class CatsViewModel : ViewModel() {
    private val repository = CatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getCats()
    }

    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }

    fun add (cat: Cat) {
        repository.add(cat)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByNameDescending() {
        repository.sortByNameDescending()
    }

    fun sortByDate() {
        repository.sortByDate()
    }

    fun sortByDateDescending() {
        repository.sortByDateDescending()
    }

    fun filterByName(name: String) {
        repository.filterByName(name)
    }
}