package com.akrivonos.a2chparser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach
import io.reactivex.disposables.Disposable
import java.util.*

class BoardsViewModel(application: Application) : AndroidViewModel(application) {
    private var listBoardsTheme: List<BoardTheme> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<BoardTheme>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext

    fun getBoardThemes(): MutableLiveData<List<BoardTheme>> {
        if (listBoardsTheme.isNotEmpty()) {
            mutableLiveData.value = listBoardsTheme
        } else {
            val observer = object : io.reactivex.Observer<BoardModel> {
                lateinit var disposable: Disposable
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(boardModel: BoardModel) {
                    boardModel.getBoardThemes(context)?.let {
                        listBoardsTheme = it
                        mutableLiveData.value = it
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    disposable.dispose()
                }
            }
            RetrofitSearchDvach.getBoards(observer)
        }
        return mutableLiveData
    }
}