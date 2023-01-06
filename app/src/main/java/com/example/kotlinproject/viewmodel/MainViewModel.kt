package com.example.kotlinproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import com.example.kotlinproject.ui.main.MainViewState
//это логика главного экрана
class MainViewModel(private val repository: Repository = Repository) :BaseViewModel<List<Note>?,MainViewState>(){
    //Обсервер LiveData следит за ответом с сервера
    private  val noteObserver = object : Observer<NoteResult>{
        override fun onChanged(t: NoteResult?) {
          if (t == null)
              return
            when (t){
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)

            }
                is NoteResult.Error -> {
                    viewStateLiveData.value = MainViewState(error =  t.error)

                }

            }

        }

    }
    private  val  repositoryNotes = repository.getNotes()
    init {
        viewStateLiveData.value = MainViewState()
        // Подписываемся на обсервер
        repositoryNotes.observeForever(noteObserver)

    }
//Отписываемся от обсервера
    override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
    }




}
//Следит за изменением заметок
//class MainViewModel : ViewModel() {
//    private  val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()
//    init {
//       // viewStateLiveData.value = MainViewState(Repository.getNotes())
//        //подписываем
//        Repository.getNotes().observeForever{
//            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it) ?: MainViewState(it)
//        }
//    }
//    //Метод получения LiveData
//   // fun viewState() :LiveData<MainViewState> = viewStateLiveData
//   fun viewState() :LiveData<MainViewState> {
//        return viewStateLiveData
//    }
//
//}