package com.example.data.dataSource.local

import com.example.data.db.dao.MovieDao
import com.example.data.db.entity.MyMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao) :
    MovieLocalDataSource {

    override fun getMyMovies(): Flow<List<MyMovie>> {
        return movieDao.selectAll()
    }

    override suspend fun addMyMovie(myMovie: MyMovie) {
        movieDao.insertMovie(myMovie)
    }

    override suspend fun deleteMyMovie(id: Int) {
        movieDao.deleteMovie(id)
    }
}

