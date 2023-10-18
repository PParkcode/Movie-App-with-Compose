package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.MyMovieDatabase
import com.example.data.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideMyMovieDatabase(
        @ApplicationContext context: Context
    ): MyMovieDatabase = Room
        .databaseBuilder(context,MyMovieDatabase::class.java,"mymovie_database")
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(myMovieDB: MyMovieDatabase): MovieDao = myMovieDB.movieDao()
}