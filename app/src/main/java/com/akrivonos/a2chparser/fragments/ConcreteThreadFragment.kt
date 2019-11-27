package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.activities.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.activities.MainActivity.Companion.NUMBER_THREAD
import com.akrivonos.a2chparser.adapters.recviewadapters.PostAdapter
import com.akrivonos.a2chparser.dagger.Injectable
import com.akrivonos.a2chparser.databinding.FragmentConcreteThreadBinding
import com.akrivonos.a2chparser.dialogs.FilterSettingsDialog
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel
import javax.inject.Inject

class ConcreteThreadFragment : Fragment(), SearchView.OnQueryTextListener, Injectable {
    private lateinit var binding: FragmentConcreteThreadBinding
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModel: ConcreteThreadViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var itemDecoratorUtils: ItemDecoratorUtils
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    private var searchView: SearchView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpAdapterAndListeners()
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConcreteThreadViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_concrete_thread, container, false)
        setHasOptionsMenu(true)
        setUpViewModel()
        setUpScreen()
        startLoadPostsForThread()
        return binding.root
    }

    private fun setUpScreen() {
       binding.concreteThreadRecycleView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 50))
            adapter = postAdapter
        }
        pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_TOOLBAR)
        toolbarModeListener?.setMode(MainActivity.Companion.ToolbarMode.FULL, "")
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
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.getPostsLiveData(idBoard, numberThread)
                            .observe(this, Observer {list-> showPostList(list) })
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun showPostList(listItems: List<FilteredItem>) {
        val listPosts: List<Post> = listItems as List<Post>
            if (listPosts.isNotEmpty()) {
                postAdapter.apply {
                    setPosts(listPosts)
                    notifyDataSetChanged()
                }
            } else {
                val error = layoutInflater.inflate(R.layout.error_message_404, null, false)
                val layoutParamsError = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                layoutParamsError.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                error.layoutParams = layoutParamsError
                view?.findViewById<RelativeLayout>(R.id.concrete_thread)?.addView(error)//TODO заменить на изменение видимости вью в лейауте
            }
            binding.progressBar.visibility = View.GONE

    }

    private fun setUpSearchView(menu: Menu) {
        menu.findItem(R.id.action_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.apply {
                isIconified = true
                setOnQueryTextListener(this@ConcreteThreadFragment)
                (this.findViewById(R.id.search_close_btn) as ImageView).setOnClickListener {
                    clearFocus()
                    postAdapter.undoFilter()
                    isIconified = true
                    isIconified = true//х2 потому что не срабатывает х1
                }
            }
        }
    }

    private fun setUpFilterButton(menu: Menu){
        menu.findItem(R.id.filter_button)?.let {
            it.setOnMenuItemClickListener {
                showFilterSettingsDialog(context, it)
                true
            }
            setUpFilterStateIcon(it, (sharedPreferenceUtils.isFilterEnable(context)))
        }
    }

    private fun showFilterSettingsDialog(context: Context?, menuItem: MenuItem?) {
        context?.let {
            FilterSettingsDialog(it, object : CallBack {
                override fun call() {
                    startLoadPostsForThread()
                    setUpFilterStateIcon(menuItem, (sharedPreferenceUtils.isFilterEnable(context)))
                }
            }).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                show()
            }
        }
    }

    private fun setUpFilterStateIcon(menuItem: MenuItem?, boolean: Boolean) {
        menuItem?.setIcon(if (boolean)
            R.drawable.filter_on
        else R.drawable.filter_off)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchView?.apply {
                clearFocus()
            }
            postAdapter.filter(it)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.length > 1) {
                postAdapter.filter(it)
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detailed_search, menu)
        setUpSearchView(menu)
        setUpFilterButton(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
