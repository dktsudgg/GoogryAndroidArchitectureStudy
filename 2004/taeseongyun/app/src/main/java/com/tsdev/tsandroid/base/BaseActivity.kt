package com.tsdev.tsandroid.base

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tsdev.presentation.base.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<VM : BaseViewModel, BINDING : ViewDataBinding> :
    AppCompatActivity() {

    protected val disposable = CompositeDisposable()

    abstract val viewModel: VM

    abstract val binding: BINDING

    inline fun movieSetDataBinding(
        @LayoutRes layoutInt: Int, activity: Activity,
        crossinline layoutBinding: (BINDING) -> BINDING
    ) =
        lazy { layoutBinding(DataBindingUtil.setContentView(activity, layoutInt)) }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}