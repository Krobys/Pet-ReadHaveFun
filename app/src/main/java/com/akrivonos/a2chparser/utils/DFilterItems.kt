package com.akrivonos.a2chparser.utils

import android.content.Context
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DFilterItems @Inject constructor(private val preferenceUtils: SharedPreferenceUtils, private var context: Context){
    //сделать контекст с даггера
    private var disposable: Disposable? = null

    fun switchFilter(){
            preferenceUtils.changeFilterThreadStatus(context)
    }

//    fun filter(originalList: List<FilteredItem>): List<FilteredItem>{
//////        val filtersList: List<FilterItem> =
////                disposable = Observable.just(originalList)
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(Schedulers.io())
////                        .map { listItems ->
////                            val tempList = ArrayList<Thread>()
////                            for (item: FilteredItem in listItems) {
////                                item.getText()?.let {
////                                    if (it.contains(textFilter)) {
////                                        tempList.add(thread)
////                                    }
////                                }
////                            }
////                            filteredThreads = tempList
////                            threads = tempList
////                        }
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribe {
////                            notifyDataSetChanged()
////                            Toast.makeText(context, "search count ${threads.size}", Toast.LENGTH_SHORT).show()
////                        }
//    }

}