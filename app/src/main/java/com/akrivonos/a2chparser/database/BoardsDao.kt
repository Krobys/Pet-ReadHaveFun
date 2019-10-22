package com.akrivonos.a2chparser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.akrivonos.a2chparser.models.database.Board
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BoardsDao {

    @Query("SELECT * FROM Board")
    fun getSavedBoardsList(): Flowable<List<Board>>

    @Insert
    fun saveBoard(board: Board): Completable

    @Query("DELETE FROM Board WHERE idBoard = (:idBoard)")
    fun deleteBoard(idBoard: String?): Completable

    @Query("SELECT * FROM Board WHERE idBoard = (:idBoard)")
    fun getBoardById(idBoard: String?): Flowable<List<Board>>
}