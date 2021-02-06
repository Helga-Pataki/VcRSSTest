package com.example.vcrsstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

class MainActivity : AppCompatActivity(),
    RSSFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.containerV, RSSFragment())
            .commit()
    }

    override fun onListFragmentInteraction(item: RssItem?) {
        val bundle = bundleOf(
            "title" to (item?.title),
            "imag" to (item?.imag),
            "pubDate" to (item?.pubDate),
            "link" to (item?.link)
        )
        val vsContentFragment = VsContentFragment()
        vsContentFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.containerV, vsContentFragment)
            .addToBackStack(null).commit()
    }
}



