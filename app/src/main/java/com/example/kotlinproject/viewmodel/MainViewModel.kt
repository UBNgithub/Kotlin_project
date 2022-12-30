package com.example.kotlinproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.ui.main.MainViewState

//Следит за изменением заметок
class MainViewModel : ViewModel() {
    private  val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()
    init {
       // viewStateLiveData.value = MainViewState(Repository.getNotes())
        //подписываем
        Repository.getNotes().observeForever{
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it) ?: MainViewState(it)
        }
    }
    //Метод получения LiveData
   // fun viewState() :LiveData<MainViewState> = viewStateLiveData
   fun viewState() :LiveData<MainViewState> {
        return viewStateLiveData
    }

}