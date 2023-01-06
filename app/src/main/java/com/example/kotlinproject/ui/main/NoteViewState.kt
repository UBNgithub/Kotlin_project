package com.example.kotlinproject.ui.main

import com.example.kotlinproject.data.model.Note
import java.lang.Error

class NoteViewState(note: Note? =null, error: Throwable? = null) : BaseViewState<Note?>(note, error){
}