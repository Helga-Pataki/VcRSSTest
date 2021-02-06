package com.example.vcrsstest.listViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vcrsstest.RssItem

class RssFragmentViewModel: ViewModel() {

    private lateinit var rssItems : MutableLiveData<List<RssItem>>
    fun fetchRssItems(): LiveData<List<RssItem>> {
        if(!::rssItems.isInitialized) {
            rssItems = MutableLiveData()
            rssItems = loadRssItems()
        }
        return rssItems
    }
    private fun loadRssItems() : MutableLiveData<List<RssItem>>{
        rssItems = VcRssRepo.instance.fetchRSSData()
        return rssItems
    }


}