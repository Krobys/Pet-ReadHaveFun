package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.PostAdapter
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils

class ConcreteThreadFragment : Fragment(), ShowContentMediaListener {

    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private lateinit var postAdapter: PostAdapter
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
        view.findViewById<RecyclerView>(R.id.concrete_thread_recycle_view)?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 50))
            adapter = postAdapter
        }
    }

    private fun setUpAdapterAndListeners() {
        activity?.let {
            pageDisplayModeListener = it as PageDisplayModeListener
            toolbarModeListener = it as SetUpToolbarModeListener
            val showContentListener = this as ShowContentMediaListener
            postAdapter = PostAdapter(it, showContentListener)
        }
    }

    private fun setUpViewModel() {

    }

    override fun showContent(pathMedia: String?, mediaType: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
