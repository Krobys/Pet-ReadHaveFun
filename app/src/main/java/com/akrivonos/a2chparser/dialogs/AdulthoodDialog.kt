package com.akrivonos.a2chparser.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.interfaces.CallBack
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils

class AdulthoodDialog(context: Context, private val callBack: CallBack) : Dialog(context) {

    private val onClickListenerChoose = View.OnClickListener { view ->
        var adultSetting = false
        when (view.id) {
            R.id.btn_accept -> adultSetting = true
            R.id.btn_cancel -> adultSetting = false
        }
        SharedPreferenceUtils.setAdultSetting(context, adultSetting)
        dismiss()
    }

    override fun dismiss() {
        super.dismiss()
        callBack.call()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_adulthood)
        val accept = findViewById<TextView>(R.id.btn_accept)
        accept.setOnClickListener(onClickListenerChoose)
        val cancel = findViewById<TextView>(R.id.btn_cancel)
        cancel.setOnClickListener(onClickListenerChoose)
    }
}
