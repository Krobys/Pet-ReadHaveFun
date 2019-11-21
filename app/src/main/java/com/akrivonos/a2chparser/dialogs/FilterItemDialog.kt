package com.akrivonos.a2chparser.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.interfaces.CallBackP
import kotlinx.android.synthetic.main.add_filter_item_dialog.*

class FilterItemDialog(context: Context, callBack: CallBackP<String>) : Dialog(context) {

    private val onClickListenerChoose = View.OnClickListener { view ->
        when(view.id){
            R.id.save_filter_item -> {
                filter_edittext.text.toString().let{
                    if (validateFilterValue(it)){
                        callBack.call(it)
                        dismiss()
                    }
                }
            }
            R.id.cancel_filter_item -> {
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.add_filter_item_dialog)
        save_filter_item.setOnClickListener(onClickListenerChoose)
        cancel_filter_item.setOnClickListener(onClickListenerChoose)
    }

    private fun validateFilterValue(filterText: String?): Boolean{
        filterText?.let{
            when(true){
                TextUtils.isEmpty(it) ->{
                    error_message.text = "Введите что нибудь в поле выше!"
                    return false
                }
                it.length > 20 -> {
                    error_message.text = "Длина превышает 20 символов"
                    return false
                }
            }
            return true
        }
        return false
    }
}