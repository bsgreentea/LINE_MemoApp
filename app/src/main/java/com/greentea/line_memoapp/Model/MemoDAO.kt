package com.greentea.line_memoapp.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo)

//    @Query("DELETE FROM memo_table")
//    suspend fun delete()

    @Query("DELETE FROM memo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM memo_table ORDER BY memoId")
    fun getAllMemo(): LiveData<List<Memo>>
}