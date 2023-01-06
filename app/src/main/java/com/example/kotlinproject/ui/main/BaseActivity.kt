package com.example.kotlinproject.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.kotlinproject.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe(this, object : Observer<S> {
            override fun onChanged(t: S?) {
                if (t == null) return
                if (t.data != null) renderData(t.data!!)
                if (t.error != null) renderError(t.error)
            }
        })
    }

    private fun renderError(error: Throwable) {
        if (error.message != null) showError(error.message!!)

    }

    protected fun showError(message: String) {
        val snackbar = Snackbar.make(mainRecycler, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Ok", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()

    }

    abstract fun renderData(data: T)

}