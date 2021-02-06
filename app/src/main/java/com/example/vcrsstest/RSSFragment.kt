package com.example.vcrsstest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.vcrsstest.listViewModel.RssFragmentViewModel

class RSSFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    var adapter: MyItemRecyclerViewAdapter? = null
    var rssItems = ArrayList<RssItem>()

    var listV: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnListFragmentInteractionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val srLayout: SwipeRefreshLayout =
            inflater.inflate(R.layout.fragment_item_list, container, false) as SwipeRefreshLayout
        val myCallback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rssItems.removeAt(viewHolder.adapterPosition)
                adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }
        listV = srLayout.findViewById(R.id.list)
        srLayout.setOnRefreshListener {
            reRss()
            srLayout.isRefreshing = false
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(listV)

        return srLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MyItemRecyclerViewAdapter(rssItems, listener, activity)
        listV?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listV?.adapter = adapter
        reRss()

    }


    private fun reRss() {
        rssItems.clear()
        val model = ViewModelProvider(this).get(RssFragmentViewModel::class.java)

        model.fetchRssItems().observe(viewLifecycleOwner,  {
            if (!it.isNullOrEmpty()) {

                rssItems.addAll(it)
                adapter?.notifyDataSetChanged()

            }
        })

    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: RssItem?)
    }

}