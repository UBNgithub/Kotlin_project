package com.example.kotlinproject.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

import com.example.kotlinproject.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int
    abstract  val ui : ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(layoutRes)
        setContentView(ui.root)
//        viewModel.getViewState().observe(this, object : Observer<S> {
//            override fun onChanged(t: S?) {
//                if (t == null) return
//                if (t.data != null) renderData(t.data!!)
//                if (t.error != null) renderError(t.error)

        // Рефакторим
        viewModel.getViewState().observe(this) {t ->
            t?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }

            }
        }


    }

    private fun renderError(error: Throwable) {
       // if (error.message != null) showError(error.message!!)
        error.message?. let {  showError(message = it)}

    }

    protected fun showError(message: String) {
//        val snackbar = Snackbar.make(mainRecycler, message, Snackbar.LENGTH_INDEFINITE)
//        snackbar.setAction("Ok", View.OnClickListener {
//            snackbar.dismiss()
//        })
//        snackbar.show()
        Snackbar.make(mainRecycler, message,Snackbar.LENGTH_INDEFINITE).apply {
            setAction("Ok") { dismiss() }
            show()
        }

    }

    abstract fun renderData(data: T)

}