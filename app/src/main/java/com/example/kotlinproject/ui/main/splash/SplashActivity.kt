package com.example.kotlinproject.ui.main.splash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivitySplashBinding
import com.example.kotlinproject.ui.main.BaseActivity
import com.example.kotlinproject.ui.main.MainActivity
import com.example.kotlinproject.ui.main.NoteActivity.Companion.getStartIntent
import com.example.kotlinproject.ui.main.RC_SIGN_IN
import com.example.kotlinproject.viewmodel.BaseViewModel


private  const val START_DELAY = 1000L
class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {


    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int=
        R.layout.activity_splash

    override val ui: ActivitySplashBinding
    by lazy {ActivitySplashBinding.inflate(layoutInflater)}

    override fun renderData(data: Boolean?) {
     data?.takeIf { it }?.let {
         startMainActivity() }
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({viewModel.requestUser()}, START_DELAY)
    }

    private fun startMainActivity() {
      startActivity(MainActivity.getStartIntent(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }
}