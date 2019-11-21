package com.akrivonos.a2chparser.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.databinding.AdapteritemFilteritemBinding
import com.akrivonos.a2chparser.interfaces.ItemRemoveListener
import com.akrivonos.a2chparser.models.database.FilterItem

class FilterItemViewHolder(private var binding: AdapteritemFilteritemBinding, private val removeItemListener: ItemRemoveListener) : RecyclerView.ViewHolder(binding.root) {

    fun removeFilterItem(filterItem: FilterItem){
        removeItemListener.remove(filterItem)
    }

    fun setFilterItem(filterItem: FilterItem){
        binding.filterItem = filterItem
    }
}