package com.example.vcrsstest.contentViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContentItemViewModel(val itemLink:String): ViewModel() {
 var rssIteML : MutableLiveData<String> =  loadContent()


fun loadContent() : MutableLiveData<String>{
    val urlC: String = itemLink
    rssIteML = ContentItemRepo.instance.fetchItemContent(urlC)
        return rssIteML
    }
}