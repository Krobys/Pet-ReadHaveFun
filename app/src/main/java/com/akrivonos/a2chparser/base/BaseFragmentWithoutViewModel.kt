package com.akrivonos.a2chparser.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.akrivonos.a2chparser.BuildConfig
import com.akrivonos.a2chparser.provider.AppProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

abstract class BaseFragmentWithoutViewModel<B : ViewDataBinding> : DaggerFragment() {
    protected lateinit var binding: B
    protected abstract val layoutId: Int
    protected var progressBar: ProgressBar? = null
    private var snackBar: Snackbar? = null
    private var baseActionListener: BaseActionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.executePendingBindings()
        return binding.root
    }

    private val chainErrorReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ERROR_ACTION) {
                if (snackBar == null) {
                    snackBar = Snackbar.make(binding.root, "Error happened", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Try again") {
                                snackBar = null
                                AppProvider.provideRetrySubject().onNext(Unit)
                                progressBar?.visibility = View.VISIBLE
                            }
                    snackBar?.show()
                }
                progressBar?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpScreen()
    }

    abstract fun setUpScreen()

    override fun onResume() {
        super.onResume()
        activity?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(chainErrorReceiver, IntentFilter(ERROR_ACTION))
        }
    }

    override fun onPause() {
        super.onPause()
        activity?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(chainErrorReceiver)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActionListener = activity as? BaseActionListener
    }

    override fun onDetach() {
        super.onDetach()
        snackBar?.dismiss()
        baseActionListener = null
    }

    companion object {
        const val ERROR_ACTION = "${BuildConfig.APPLICATION_ID}.action.ERROR"
    }
}