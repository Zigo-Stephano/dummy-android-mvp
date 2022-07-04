package com.app.baseproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.baseproject.data.model.local.*

@Database(entities = [User::class], version = 1)
abstract class DataDatabase: RoomDatabase() {

    abstract fun dataDao() : DataDao?

    companion object {

        @Volatile
        private var INSTANCE: DataDatabase? = null

        fun getInstance(context: Context) :DataDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    DataDatabase::class.java, "training.db")
                    .fallbackToDestructiveMigration()
                    .build()
    }

}