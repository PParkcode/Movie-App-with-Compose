package com.example.data.repository

import com.example.data.dataSource.MovieDataSource
import com.example.data.mapper.toCover
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieCoverList
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class MovieRepositoryImpl  @Inject constructor(
    private val dataSource: MovieDataSource
): MovieRepository {
    override suspend fun getPopularMovie(): List<MovieCover> {
        var movieList = dataSource.getPopularMovie()

        val movieCoverList = movieList.map { it ->
            toCover(it.title,it.poster_path,it.vote_average.toString())}
        return movieCoverList
    }
}