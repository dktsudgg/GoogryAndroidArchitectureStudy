package mi.song.class12android.data.repository

import android.content.Context
import mi.song.class12android.data.model.MovieInfo
import mi.song.class12android.data.model.MovieResponse
import mi.song.class12android.data.source.remote.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMovieRepositoryImpl(context: Context) : SearchMovieRepository {
    private val movieService = RetrofitHelper.getService(context)

    override fun getRemoteMovieData(
        query: String,
        success: (List<MovieInfo>) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        movieService.getMovieInfo(query).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                fail(t)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.let { mr ->
                    success(mr.items)
                }
            }

        })
    }
}