package com.greentea.line_memoapp.AppDB

import androidx.lifecycle.LiveData
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Model.MemoDAO

class MemoRepository(private val memoDAO: MemoDAO){

    val allMemos: LiveData<List<Memo>> = memoDAO.getAllMemo()

    suspend fun insert(memo: Memo){
        memoDAO.insert(memo)
    }

    suspend fun deleteAll(){
        memoDAO.deleteAll()
    }
}