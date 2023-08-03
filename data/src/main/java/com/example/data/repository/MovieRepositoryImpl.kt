package com.example.data.repository

import com.example.data.dataSource.MovieDataSource
import com.example.data.mapper.toCover
import com.example.data.model.MovieData
import com.example.domain.model.MovieCover
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class MovieRepositoryImpl  @Inject constructor(
    private val dataSource: MovieDataSource
): MovieRepository {
    override suspend fun getPopularMovie(): List<MovieCover> {
        var movieList = dataSource.getPopularMovie()

        return toMovieCover(movieList)
    }

    override suspend fun getNowPlaying(): List<MovieCover> {
        var movieList = dataSource.getNowPlaying()
        return toMovieCover(movieList)
    }


    override suspend fun getUpComing(): List<MovieCover> {
        var movieList = dataSource.getUpComing()
        return toMovieCover(movieList)
    }

    override suspend fun getTopRated(): List<MovieCover> {
        var movieList = dataSource.getTopRated()
        return toMovieCover(movieList)
    }

    override suspend fun getSearchResult(keyword:String): List<MovieCover> {
        var movieList = dataSource.getSearchResult(keyword)
        return toMovieCover(movieList)
    }

    override suspend fun getFilterMovies(withGenres:String, Stars:String, withoutGenres:String): List<MovieCover> {
        val movieList = dataSource.getFilterMovies(withGenres, Stars, withoutGenres)
        return toMovieCover(movieList)
    }



    private fun toMovieCover(movieList: List<MovieData>):List<MovieCover> {
        val movieCoverList = movieList.map { it ->
            if(it.poster_path == null) {
                toCover(it.title,"",it.vote_average.toString())
            } else {
                toCover(it.title,it.poster_path,it.vote_average.toString())
            }

        }
        return movieCoverList
    }


}