package com.hong.architecturestudy.data.source.local

import android.content.Context
import com.hong.architecturestudy.data.source.local.entity.MovieInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataSourceImpl(context: Context) : LocalDataSource {
    private var movieDB: MovieDatabase? = MovieDatabase.getInstance(context)

    override fun saveData(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val movieInfo = MovieInfo()
            movieInfo.movieTitle = keyword
            movieDB?.movieDao()?.insert(movieInfo)
        }
    }

    override fun loadData(): List<MovieInfo> {
        return movieDB?.movieDao()?.getAll()!!
    }

}