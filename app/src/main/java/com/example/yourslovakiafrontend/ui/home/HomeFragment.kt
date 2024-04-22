package com.example.yourslovakiafrontend.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourslovakiafrontend.R
import com.example.yourslovakiafrontend.view_adapter.RecyclerViewAdapter
import fiit.mtaa.yourslovakia.models.PointOfInterest

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dataList = getDataList() // Your function to fetch data
        val adapter = RecyclerViewAdapter(dataList, requireContext())
        recyclerView.adapter = adapter

        return rootView
    }

    private fun getDataList(): List<PointOfInterest> {
        // Implement your logic to fetch data and create CustomObject instances
        // For demonstration, let's create a dummy list
        val dataList = mutableListOf<PointOfInterest>()
        for (i in 1..10) {
            dataList.add(PointOfInterest(R.drawable.baseline_image_not_available_24, "Item $i", "Description $i"))
        }
        return dataList
    }
}