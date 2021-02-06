package com.example.vcrsstest.contentViewModel

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class ContentItemRepo private constructor() {
    private object HOLDER {
        val INSTANCE = ContentItemRepo()
    }
    companion object {
        val instance: ContentItemRepo by lazy { HOLDER.INSTANCE }
    }

    fun fetchItemContent(urlC: String?): MutableLiveData<String> {
        val dataC = MutableLiveData<String>()
        ItemContentFetcher(dataC).execute(urlC)
        return dataC
    }

    class ItemContentFetcher(dataC: MutableLiveData<String>) :
        AsyncTask<String, Void, String>() {
        var str1: String = ""
        var dataItemContent: MutableLiveData<String> = dataC

        override fun doInBackground(vararg p0: String?): String? {
            val doc: Document
            val docAMP: Document
            try {
                doc = Jsoup.connect(p0[0]).get()
                val ampLinks: Elements? = doc.select("link").attr("rel", "amphtml")
                val amplink1: String = ampLinks!![3].attr("href")
                docAMP = Jsoup.connect(amplink1).get()

                val textElements: Elements = docAMP.select("div.l-island-a")
                    .select("p")

                for (textElement in textElements) {
                    str1 += textElement.text()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return str1
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (!result.isEmpty()) {
                dataItemContent.value = result
            }
        }
    }

}