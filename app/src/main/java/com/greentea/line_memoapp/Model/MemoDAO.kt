package com.greentea.line_memoapp.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Query("DELETE FROM memo_table")
    fun delete()

    @Delete
    fun deleteAll()

    @Query("SELECT * FROM memo_table ORDER BY memoId")
    fun getAllMemo(): LiveData<List<Memo>>
}