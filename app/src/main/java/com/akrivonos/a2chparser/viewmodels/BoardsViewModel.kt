package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class BoardsViewModel @Inject constructor(private var retrofit: RetrofitSearch, private var context: Context) : ViewModel() {
    private var listBoardsTheme: List<BoardTheme> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<BoardTheme>?> = MutableLiveData()

    fun getBoardThemes(): MutableLiveData<List<BoardTheme>?> {
        if (listBoardsTheme.isNotEmpty()) {
            Log.d("test", "isNot Em: ")
            mutableLiveData.value = listBoardsTheme
        } else {
            val observer = object : io.reactivex.Observer<BoardModel> {
                lateinit var disposable: Disposable
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(boardModel: BoardModel) {
                    Log.d("test", "onNext: ")
                    boardModel.getBoardThemes(context)?.let {
                        listBoardsTheme = it
                        mutableLiveData.value = it
                    } ?: mutableLiveData.setValue(null)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    disposable.dispose()
                }
            }

            retrofit.getBoards(observer)
        }
        return mutableLiveData
    }
}