package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.databinding.AdapteritemFilteritemBinding
import com.akrivonos.a2chparser.interfaces.ItemRemovable
import com.akrivonos.a2chparser.interfaces.ItemRemoveListener
import com.akrivonos.a2chparser.models.database.FilterItem
import com.akrivonos.a2chparser.viewholders.FilterItemViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FilterItemAdapter(context: Context) : RecyclerView.Adapter<FilterItemViewHolder>(), ItemRemoveListener {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val appDatabase: RoomAppDatabase? = RoomAppDatabase.getAppDataBase(context)
    private var listFilters: ArrayList<FilterItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        val binding: AdapteritemFilteritemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.adapteritem_filteritem, parent, false)
        val removeItemListener: ItemRemoveListener = this
        return FilterItemViewHolder(binding, removeItemListener)
    }

    override fun getItemCount(): Int = listFilters.size

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.setFilterItem(listFilters[position])
    }

    fun subscribeActualFilterList() {
        appDatabase?.filterItemDao()?.getFilteredItemsList()?.let { obs ->
            obs
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { listFilters ->
                        this.listFilters = ArrayList(listFilters)
                        notifyDataSetChanged()
                    }
        }
    }

    override fun remove(item: ItemRemovable) {
        (item as FilterItem).let {
            appDatabase?.filterItemDao()?.removeFilteredItems(it.filterText)?.let { completable ->
                completable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val position = listFilters.indexOf(it)
                            listFilters.removeAt(position)
                            notifyItemRemoved(position)
                        }
            }
        }
    }
}