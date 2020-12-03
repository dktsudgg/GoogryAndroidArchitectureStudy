package kr.dktsudgg.androidarchitecturestudy.view.ui.mvp

import kr.dktsudgg.androidarchitecturestudy.data.model.MovieItem
import kr.dktsudgg.androidarchitecturestudy.data.model.NaverMovieResponse
import kr.dktsudgg.androidarchitecturestudy.data.repository.NaverMovieRepository

class MovieSearchPresenter(
    private val naverMovieRepository: NaverMovieRepository,
    private val view: MovieSearchContract.View
) : BasePresenter(), MovieSearchContract.Presenter {

    override fun searchMovies(query: String) {
        if (isEmptyData(query)) {
            view.showToast("검색어를 입력하세요")
            return
        }

        naverMovieRepository.searchMovies(query, {
            (it as NaverMovieResponse).items?.let { searchedMovieList ->
                // 검색된 영화 데이터로 갱신
                view.updateSearchedMovieList(searchedMovieList)
                view.showToast("검색에 성공하였습니다.")
            }
        }, {
            // 빈 데이터로 갱신
            view.updateSearchedMovieList(ArrayList<MovieItem>())
            view.showToast("검색에 실패하였습니다.")
        })
    }

}