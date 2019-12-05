package com.akrivonos.a2chparser.utils

import com.akrivonos.a2chparser.builders.SubjectBuilder
import com.akrivonos.a2chparser.database.FilterItemDao
import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.akrivonos.a2chparser.models.database.FilterItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DFilterItems @Inject constructor(private val filterItemDao: FilterItemDao) {
    private var disposable: Disposable? = null

    fun filter(originalList: List<FilteredItem>, observerFilter: io.reactivex.functions.Consumer<List<FilteredItem>>) {
        val subjectFilter = SubjectBuilder.createPublishSubject(observerFilter)
        disposable = filterItemDao.getFilteredItemsList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { listOfFilters ->
                    val filteredList: ArrayList<FilteredItem> = ArrayList(originalList)
                    if (listOfFilters.isNotEmpty()) {
                        for (filteredItem: FilteredItem in originalList) {
                            filteredItem.getText()?.toLowerCase()?.let { comment ->
                                for (filterItem: FilterItem in listOfFilters) {
                                    filterItem.filterText?.toLowerCase()?.let { filterText ->
                                        if (comment.contains(filterText)) {
                                            filteredList.remove(filteredItem)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    filteredList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    subjectFilter.onNext(list)
                }
    }

}