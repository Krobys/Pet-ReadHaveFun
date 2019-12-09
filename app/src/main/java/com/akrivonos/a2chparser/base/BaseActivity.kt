package com.akrivonos.a2chparser.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), BaseActionListener {

    protected abstract val layoutId: Int
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onDisplayMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


}