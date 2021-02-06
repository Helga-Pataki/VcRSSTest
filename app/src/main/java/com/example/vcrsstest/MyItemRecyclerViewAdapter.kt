package com.example.vcrsstest


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


class MyItemRecyclerViewAdapter(
    private val values: List<RssItem>,
    private val mListener: RSSFragment.OnListFragmentInteractionListener?,
    private val context: FragmentActivity?

) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rss, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.pubDate.text = item.pubDate
        val imgM = getImageLink(item.description)

        if (!imgM.isNullOrEmpty()) {
            item.imag = imgM
            context?.let {
                Glide.with(it)
                    .load(imgM)
                    .into(holder.image)
            }
        } else {
            holder.image.setImageResource(R.drawable.ic_vc_5)
        }
        holder.itemView.setOnClickListener {
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val viewRss: View) : RecyclerView.ViewHolder(viewRss) {
        val image: ImageView = viewRss.findViewById(R.id.featuredImg)
        val title: TextView = viewRss.findViewById(R.id.txtTitle)
        val pubDate: TextView = viewRss.findViewById(R.id.txtPubdate)

    }

    private fun getImageLink(htmlText: String): String? {
        var result: String? = null
        try {
            val doc: Document = Jsoup.parse(htmlText)
            val imgs: Elements = doc.select("img")
            val src = imgs.attr("src")
            result = src
        } catch (e: IOException) {

        }
        return result
    }

}