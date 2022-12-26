package com.example.kotlinproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.data.Repository
//Следит за изменением заметок
class MainViewModel : ViewModel() {
    private  val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()
    init {
        viewStateLiveData.value = MainViewState(Repository.getNotes())
    }
    //Метод получения LiveData
   // fun viewState() :LiveData<MainViewState> = viewStateLiveData
   fun viewState() :LiveData<MainViewState> {
        return viewStateLiveData
    }

}