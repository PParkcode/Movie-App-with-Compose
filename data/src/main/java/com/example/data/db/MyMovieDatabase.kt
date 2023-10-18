package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.db.dao.MovieDao
import com.example.data.db.entity.MyMovie


@Database(entities = [MyMovie::class],version = 1)
abstract class MyMovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    /*
    companion object {
        private var DBINSTANCE: MyMovieDatabase? = null

        fun getDBInstance(context:Context): MyMovieDatabase {
            synchronized(this) {
                var instance = DBINSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyMovieDatabase::class.java,
                        "mymovie_database",
                    ).fallbackToDestructiveMigration()
                        .build()

                    DBINSTANCE = instance
                }
                return instance

            }
        }
    }

     */


}