package com.eunice.eunicehong.ui

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eunice.eunicehong.R
import com.eunice.eunicehong.data.model.MovieList
import com.eunice.eunicehong.data.source.MovieDataSource
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MovieContract.View {
    private val cache = object : MovieCache {
        val preferences = MoviePreferences.getInstance(this@MainActivity)

        @Throws(IllegalStateException::class, JsonSyntaxException::class)
        override fun getMovieList(
            query: String
        ): MovieList = preferences.getHistory(query)

        override fun saveMovieList(query: String, movieList: MovieList) =
            preferences.saveHistory(query, movieList)


        override fun removeMovieHistory() {
            preferences.removeAllSearchHistory()
        }
    }

    override lateinit var movieContext: Context

    private lateinit var searchView: SearchView

    private val presenter = MoviePresenter(this, cache)
    private val movieListAdapter = MovieAdapter(presenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieContext = this@MainActivity

        setContentView(R.layout.activity_main)

        setMovieList()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val query = intent?.getStringExtra(SearchManager.QUERY)
        if (!query.isNullOrBlank()) {
            presenter.search(query, object : MovieDataSource.LoadMoviesCallback {
                override fun onSuccess(movieList: MovieList) {
                    showSearchResult(movieList)
                    cache.saveMovieList(query, movieList)
                }

                override fun onFailure(e: Throwable) {
                    Log.d(this.toString(), e.toString())
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search_bar).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
            isSubmitButtonEnabled = true
            isQueryRefinementEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        presenter.onOptionMenuSelected(item.itemId)

    override fun showRemoveHistoryConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(getString(R.string.delete_history_confirmation))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                presenter.removeHistory()
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.complete_delete_history),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(android.R.string.no, null).show()
    }

    override fun showSearchResult(movies: MovieList) {
        zero_item_message.visibility =
            if (movies.items.isNullOrEmpty()) View.VISIBLE else View.GONE
        movieListAdapter.setMovieList(movies.items)
    }

    private fun setMovieList() {
        move_list.adapter = movieListAdapter
    }

    override fun setQueryText(query: String) {
        searchView.setQuery(query, false)
        searchView.clearFocus()
    }

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }
}