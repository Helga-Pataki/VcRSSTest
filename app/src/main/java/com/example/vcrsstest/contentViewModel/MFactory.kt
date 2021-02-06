package com.example.vcrsstest.contentViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MFactory(val link: String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContentItemViewModel(link) as T
    }

}
