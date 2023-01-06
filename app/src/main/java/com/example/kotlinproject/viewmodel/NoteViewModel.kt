package com.example.kotlinproject.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import com.example.kotlinproject.ui.main.NoteViewState

class NoteViewModel(val repository: Repository = Repository) :
    BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null
    fun saveChange(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                if (t == null)
                    return
                when (t) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is NoteResult.Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)

                }
            }
        })

    }
}


//
//class NoteViewModel(private  val repository: Repository = Repository) : ViewModel() {
//    private var pendingNote : Note? =null
//    fun saveChange (note: Note){
//        pendingNote = note
//    }
//// Сохранение заметки при уничтжении viewModel  и activity соответственно
//    override fun onCleared() {
//        if (pendingNote != null){
//            repository.saveNote(pendingNote!!)
//        }
//    }
//}