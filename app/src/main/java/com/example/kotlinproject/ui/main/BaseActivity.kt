package com.example.kotlinproject.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.kotlinproject.R
import com.example.kotlinproject.data.error.NoAuthException

import com.example.kotlinproject.viewmodel.BaseViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

const val RC_SIGN_IN: Int = 458
abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {


    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int
    abstract val ui: ViewBinding

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
        viewModel.getViewState().observe(this) { t ->
            t?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }

            }
        }


    }
    //Обработка ошибки NoAuthError. На всех экранах одно
    protected open  fun renderError(error: Throwable) {
        // if (error.message != null) showError(error.message!!)
        // error.message?. let {  showError(message = it)}
        when (error) {
          is NoAuthException -> startLoginActivity()
            else -> error.message?.let { showError(it) }

        }

    }

    // Открываем экран авторизации
    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)


    }

    protected fun showError(message: String) {
//        val snackbar = Snackbar.make(mainRecycler, message, Snackbar.LENGTH_INDEFINITE)
//        snackbar.setAction("Ok", View.OnClickListener {
//            snackbar.dismiss()
//        })
//        snackbar.show()
        Snackbar.make(mainRecycler, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction("Ok") { dismiss() }
            show()
        }

    }

    abstract fun renderData(data: T)




}