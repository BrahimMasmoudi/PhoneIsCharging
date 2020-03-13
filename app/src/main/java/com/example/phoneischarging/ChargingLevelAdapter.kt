package com.example.phoneischarging

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.charging_level_items.view.*

class ChargingLevelAdapter(private val activity: MainActivity, private val chargingLevelList: List<Int>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = activity.layoutInflater.inflate(R.layout.charging_level_items, parent, false)

        view.charging_level.text = chargingLevelList[position].toString()
        return view
    }

    override fun getItem(position: Int) = chargingLevelList[position]

    override fun getItemId(position: Int): Long = 0L

    override fun getCount() = chargingLevelList.size
}