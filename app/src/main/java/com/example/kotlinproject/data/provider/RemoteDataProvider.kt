package com.example.kotlinproject.data.provider

import androidx.lifecycle.LiveData
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String) :LiveData<NoteResult>
    fun saveNote(note: Note) :LiveData<NoteResult>

}