package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener

class ConcreteThreadFragment : Fragment() {
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpAdapterAndListeners()
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_concrete_thread, container, false)
        setUpScreen(view)
        return view
    }

    private fun setUpScreen(view: View) {

    }

    private fun setUpAdapterAndListeners() {

    }

    private fun setUpViewModel() {

    }
}
