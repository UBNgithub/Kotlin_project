package com.example.kotlinproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.data.model.Note

class NoteViewModel(private  val repository: Repository = Repository) : ViewModel() {
    private var pendingNote : Note? =null
    fun saveChange (note: Note){
        pendingNote = note
    }
// Сохранение заметки при уничтжении viewModel  и activity соответственно
    override fun onCleared() {
        if (pendingNote != null){
            repository.saveNote(pendingNote!!)
        }
    }
}