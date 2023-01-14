package com.example.kotlinproject.ui.main.splash

import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.data.error.NoAuthException
import com.example.kotlinproject.viewmodel.BaseViewModel

class SplashViewModel(private val repository: Repository = Repository)
    : BaseViewModel<Boolean?, SplashViewState>() {
    //Авторизован пользователь или нет
        fun requestUser(){
            repository.getCurrentUser().observeForever(){ user ->
                viewStateLiveData.value = if (user !=null){
                    SplashViewState(isAuth = true)
                }else {
                    SplashViewState(error =  NoAuthException())
                }
            }
        }
}