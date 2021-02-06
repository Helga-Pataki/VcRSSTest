package com.example.vcrsstest.listViewModel

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.example.vcrsstest.RssItem
import com.example.vcrsstest.RssParser
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class VcRssRepo private constructor() {

    private val VCRSS = "https://vc.ru/rss/all"

    private object HOLDER {
        val INSTANCE = VcRssRepo()
    }

    companion object {
        val instance: VcRssRepo by lazy { HOLDER.INSTANCE }
    }


    fun fetchRSSData(): MutableLiveData<List<RssItem>> {
        val url = URL(VCRSS)
        val data = MutableLiveData<List<RssItem>>()
        RssFeedFetcher(data).execute(url)
        return data
    }


    class RssFeedFetcher(data: MutableLiveData<List<RssItem>>) :
        AsyncTask<URL, Void, List<RssItem>>() {
        private var stream: InputStream? = null;
        val dataListRssItem: MutableLiveData<List<RssItem>> = data

        override fun doInBackground(vararg params: URL?): List<RssItem>? {
            val connect = params[0]?.openConnection() as HttpsURLConnection
            connect.readTimeout = 4000
            connect.connectTimeout = 4000
            connect.connect();

            val responseCode: Int = connect.responseCode;
            var rssItems: List<RssItem>? = null
            if (responseCode == 200) {
                stream = connect.inputStream;

                try {
                    val parser = RssParser()
                    rssItems = parser.parse(stream!!)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            connect.disconnect()
            return rssItems
        }

        override fun onPostExecute(result: List<RssItem>?) {
            super.onPostExecute(result)
            if (result != null && !result.isEmpty()) {
                dataListRssItem.value = result
            }
        }
    }
}