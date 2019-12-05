package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.base.BaseViewModel
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class BoardsViewModel @Inject constructor(private var retrofit: ApiRetrofitInterface,
                                          private var context: Context) : BaseViewModel() {
    private var listBoardsTheme: List<BoardTheme> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<BoardTheme>> = MutableLiveData()

    fun getBoardThemes(): MutableLiveData<List<BoardTheme>> {
        if (listBoardsTheme.isNotEmpty()) {
            mutableLiveData.value = listBoardsTheme
        } else {
            Timber.d("getBoards")
            disposable += retrofit.getBoards()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.d("Error timber")
                        Timber.d(it)
                        messageEvent.postValue(it.message)
                    }
                    .subscribeBy(onSuccess = { boardModel ->
                        boardModel.getBoardThemes(context)?.let {
                            Timber.d("Success")
                            listBoardsTheme = it
                            mutableLiveData.value = it
                        }
                    })

        }
        return mutableLiveData
    }
}