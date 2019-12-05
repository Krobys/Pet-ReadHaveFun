package com.akrivonos.a2chparser.base

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.akrivonos.a2chparser.BuildConfig

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), BaseActionListener {
    companion object {
        const val ERROR_ACTION = "${BuildConfig.APPLICATION_ID}.action.ERROR"
        const val RELOAD_ACTION = "${BuildConfig.APPLICATION_ID}.action.RELOAD"
    }

    protected abstract val layoutId : Int
    protected lateinit var binding : B



    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onDisplayMessage(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ERROR_ACTION)

    }

    override fun onPause() {
        super.onPause()

    }
}