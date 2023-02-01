package com.example.kotlinproject.ui.main

import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import java.lang.Error

//class NoteViewState(note: Note? =null, error: Throwable? = null) : BaseViewState<Note?>(note, error){
//}
class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data , error){
    data class Data(val isDeleted: Boolean = false, val note: Note? =null)
}