package com.rxchainretrier.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.akrivonos.a2chparser.BR
import com.akrivonos.a2chparser.BuildConfig
import com.akrivonos.a2chparser.base.BaseActionListener
import com.akrivonos.a2chparser.base.BaseViewModel
import com.akrivonos.a2chparser.dagger.Injectable
import com.akrivonos.a2chparser.nonNullObserve
import com.akrivonos.a2chparser.provider.AppProvider
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment(), Injectable {
    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VM>
    protected abstract var progressBar: ProgressBar?
    protected lateinit var binding: B
    private var snackBar: Snackbar? = null
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }
    private var baseActionListener: BaseActionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, viewModel)
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

    abstract fun doAfterCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doAfterCreateView()
        viewModel.messageEvent.nonNullObserve(this) {
            Timber.d("messageEvent")
            baseActionListener?.onDisplayMessage(it)
            progressBar?.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(chainErrorReceiver, IntentFilter(ERROR_ACTION))
        }
    }

    override fun onPause() {
        super.onPause()
        snackBar?.view?.visibility = View.INVISIBLE
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
        baseActionListener = null
    }

    companion object {
        const val ERROR_ACTION = "${BuildConfig.APPLICATION_ID}.action.ERROR"
    }
}