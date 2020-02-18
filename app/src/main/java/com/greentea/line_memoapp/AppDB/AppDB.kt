package com.greentea.line_memoapp.AppDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Model.MemoDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

@Database(entities = [Memo::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun memoDAO(): MemoDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDB(context: Context,
                  scope: CoroutineScope
        ): AppDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "app_db"
                )
                    .addCallback(AppDBCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDBCallBack(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDB(database.memoDAO())
                    }
                }
            }
        }

        suspend fun populateDB(memoDAO: MemoDAO){
            memoDAO.deleteAll()


        }
    }
}