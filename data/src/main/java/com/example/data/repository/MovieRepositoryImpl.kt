package com.example.data.repository

import com.example.data.dataSource.local.MovieLocalDataSource
import com.example.data.dataSource.remote.MovieDataSource
import com.example.data.db.entity.MyMovie
import com.example.data.mapper.toCover
import com.example.data.mapper.toDetail
import com.example.data.model.MovieData
import com.example.domain.model.MovieCover
import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {
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

    override suspend fun getSearchResult(keyword: String): List<MovieCover> {
        var movieList = dataSource.getSearchResult(keyword)
        return toMovieCover(movieList)
    }

    override suspend fun getFilterMovies(
        withGenres: String,
        Stars: String,
        withoutGenres: String
    ): List<MovieCover> {
        val movieList = dataSource.getFilterMovies(withGenres, Stars, withoutGenres)
        return toMovieCover(movieList)
    }

    override suspend fun getDetailMovieData(id: Int): MovieDetail {
        val movieData = dataSource.getDetailMovieData(id)
        return toDetail(movieData)
    }

    override suspend fun getRecommendations(id: Int): List<MovieCover> {
        val movieList = dataSource.getRecommendations(id)
        return toMovieCover(movieList)
    }

    override suspend fun getSimilar(id: Int): List<MovieCover> {
        val movieList = dataSource.getSimilar(id)
        return toMovieCover(movieList)
    }


    //DB
    override suspend fun insertMovie(id: Int, title: String, poster_path: String) {
        localDataSource.addMyMovie(MyMovie(id,title,poster_path))
    }

    override suspend fun deleteMovie(id: Int) {
        localDataSource.deleteMyMovie(id)
    }

    override fun getMyMovies(): Flow<List<MovieCover>> {


        val movieList = localDataSource.getMyMovies()
        val movieCoverList = movieList.map { myMovies ->
            myMovies.map { myMovie ->
                toCover(
                    title = myMovie.movie_title,
                    url = myMovie.poster_path,
                    id = myMovie.id,
                    star = ""
                )
            }

        }
        return movieCoverList


    }

    private fun toMovieCover(movieList: List<MovieData>): List<MovieCover> {
        val movieCoverList = movieList.map { it ->
                toCover(
                    title = it.title,
                    url = it.poster_path ?: "",
                    id = it.id,
                    star = it.vote_average.toString()?:"",
                )
        }
        return movieCoverList
    }

}