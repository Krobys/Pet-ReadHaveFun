package com.akrivonos.a2chparser.database

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    fun deleteBoard(board: Board): Completable

    @Query("SELECT * FROM Board WHERE idBoard = (:id)")
    fun getBoardById(id: String): Flowable<List<Board>>
}