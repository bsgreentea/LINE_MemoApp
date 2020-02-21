package com.greentea.line_memoapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.greentea.line_memoapp.AppDB.AppDB
import com.greentea.line_memoapp.AppDB.MemoRepository
import com.greentea.line_memoapp.Model.Memo
import kotlinx.coroutines.launch

class MemoViewModel(application: Application): AndroidViewModel(application){

    private val repository: MemoRepository

    val allMemos: LiveData<List<Memo>>

    init{
        val memoDAO = AppDB.getDB(application, viewModelScope).memoDAO()
        repository = MemoRepository(memoDAO)
        allMemos = repository.allMemos
    }

    fun insert(memo: Memo) = viewModelScope.launch{
        repository.insert(memo)
    }

    fun delete(memo: Memo) = viewModelScope.launch {
        repository.delete(memo)
    }
}