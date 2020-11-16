package kr.dktsudgg.androidarchitecturestudy.data.datasource.remote

import kr.dktsudgg.androidarchitecturestudy.data.model.NaverMovieResponse

interface NaverMovieRemoteDataSource {

    fun searchMovies(
        query: String,
        display: Int?,
        start: Int?,
        successCallback: (NaverMovieResponse) -> Unit,
        failureCallback: (t: Throwable) -> Unit
    )

}