package com.akrivonos.a2chparser.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.FilterItemAdapter
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.interfaces.CallBack
import com.akrivonos.a2chparser.interfaces.CallBackP
import com.akrivonos.a2chparser.models.database.FilterItem
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.filter_settings.*

class FilterSettingsDialog(context: Context, private var callBack: CallBack) : Dialog(context) {

    private var sharedPreferenceUtils: SharedPreferenceUtils = SharedPreferenceUtils()
    private var database = RoomAppDatabase.getAppDataBase(context)
    private var filterAdapter: FilterItemAdapter = FilterItemAdapter(context)
    private val onClickListenerChoose = View.OnClickListener { view ->
        when(view.id){
            R.id.switch_filter -> {}
            R.id.save_button -> {
                sharedPreferenceUtils.setFilterStatusEnabled(context, switch_filter.isChecked)
                callBack.call()
                dismiss()
            }
            R.id.add_filter_button ->{
                showFilterSettingsDialog(getContext())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.filter_settings)
        switch_filter.setOnClickListener(onClickListenerChoose)
        switch_filter.isChecked = sharedPreferenceUtils.isFilterEnable(context)
        save_button.setOnClickListener(onClickListenerChoose)
        add_filter_button.setOnClickListener(onClickListenerChoose)
        filter_recview?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filterAdapter
            filterAdapter.refreshActualFilterList()
        }
    }

    private fun showFilterSettingsDialog(context: Context?) {
        context?.let {
            FilterItemDialog(it, object : CallBackP<String> {
                override fun call(t: String) {
                    FilterItem().let { filterItem ->
                        filterItem.filterText = t
                        database?.filterItemDao()?.addFilteredItem(filterItem)?.let{completable ->
                            completable
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe{filterAdapter.refreshActualFilterList()}
                        }
                    }
                }
            }).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                show()
                Log.d("test", "show dialog FilterItemDialog:")
            }
        }
    }
}