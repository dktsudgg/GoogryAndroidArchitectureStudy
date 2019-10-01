package study.architecture.myarchitecture.ui.main

import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import study.architecture.myarchitecture.R
import study.architecture.myarchitecture.base.BaseActivity
import study.architecture.myarchitecture.data.Injection
import study.architecture.myarchitecture.databinding.ActivityMainBinding
import study.architecture.myarchitecture.util.Filter

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by lazy {
        MainViewModel(Injection.provideFolderRepository(this))
    }

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initViewPager()
        initAdapterCallback()
        mainViewModel.loadData()
    }

    override fun onDestroy() {
        mainViewModel.clearDisposable()
        super.onDestroy()
    }


    private fun initViewModel() {
        binding.mainModel = mainViewModel
    }

    private fun initViewPager() {
        binding.vpMain.adapter = mainAdapter
    }

    @Suppress("UNCHECKED_CAST")
    private fun initAdapterCallback() {
        mainViewModel.selectField.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {

                (sender as ObservableField<Pair<Filter.SelectArrow, Boolean>>).get()?.let {

                    val (filter, selected) = it

                    val order = if (selected) {
                        Filter.ASC
                    } else {
                        Filter.DESC
                    }

                    for (i in 0 until (mainAdapter.count)) {
                        mainAdapter.getFragment(i)?.let { fragment ->
                            fragment.get()?.showTickerListOrderByField(filter, order)
                        }
                    }
                }
            }

        })
    }
}