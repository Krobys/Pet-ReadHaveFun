package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity
import com.akrivonos.a2chparser.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.MainActivity.Companion.NUMBER_THREAD
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.PostAdapter
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel

class ConcreteThreadFragment : Fragment() {

    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private lateinit var viewModel: ConcreteThreadViewModel
    private lateinit var postAdapter: PostAdapter
    private lateinit var progressBar: ProgressBar
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpAdapterAndListeners()
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_concrete_thread, container, false)
        setUpScreen(view)
        startLoadPostsForThread()
        return view
    }

    private fun setUpScreen(view: View) {
        view.findViewById<RecyclerView>(R.id.concrete_thread_recycle_view)?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 50))
            adapter = postAdapter
        }
        progressBar = view.findViewById(R.id.progressBar)
        pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_TOOLBAR)
        toolbarModeListener?.setMode(MainActivity.Companion.ToolbarMode.BACK_BUTTON, "")
    }

    private fun setUpAdapterAndListeners() {
        activity?.let {
            pageDisplayModeListener = it as PageDisplayModeListener
            toolbarModeListener = it as SetUpToolbarModeListener
            val showContentListener = it as ShowContentMediaListener
            postAdapter = PostAdapter(it, showContentListener)
        }
    }

    private fun startLoadPostsForThread() {
        arguments?.let {
            it.getString(ID_BOARD)?.let { idBoard ->
                it.getString(NUMBER_THREAD)?.let { numberThread ->
                    viewModel.getPostsLiveData(idBoard, numberThread)
                            .observe(this, androidx.lifecycle.Observer<List<Post>> { listPosts ->
                                if (listPosts.isNotEmpty()) {
                                    postAdapter.apply {
                                        postsList = listPosts
                                        notifyDataSetChanged()
                                    }
                                } else {
                                    val error = layoutInflater.inflate(R.layout.error_message_404, null, false)
                                    val layoutParamsError = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                                    layoutParamsError.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                                    error.layoutParams = layoutParamsError
                                    view?.findViewById<RelativeLayout>(R.id.concrete_thread)?.addView(error)
                                }
                                progressBar.visibility = View.GONE
                            })
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(ConcreteThreadViewModel::class.java)
    }

}
