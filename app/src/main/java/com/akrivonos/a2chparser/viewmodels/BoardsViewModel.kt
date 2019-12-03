package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.provider.AppProvider
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import com.rxchainretrier.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class BoardsViewModel @Inject constructor(private var context: Context) : BaseViewModel() {
    private var listBoardsTheme: List<BoardTheme> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<BoardTheme>> = MutableLiveData()
    val retrofit : ApiRetrofitInterface = AppProvider.provideApiService()

    fun getBoardThemes(): MutableLiveData<List<BoardTheme>> {
        if (listBoardsTheme.isNotEmpty()) {
            mutableLiveData.value = listBoardsTheme
        } else {
            disposable += retrofit.getBoardsa()
                    .subscribeOn(Schedulers.io())
                    .subscribeBy(onSuccess = { boardModel ->
                        boardModel.getBoardThemes(context)?.let {
                            listBoardsTheme = it
                            mutableLiveData.value = it
                        }
                    }, onError = {
                        Timber.d(it)
                        messageEvent.postValue(it.message)
                    })
        }
        return mutableLiveData
    }
}