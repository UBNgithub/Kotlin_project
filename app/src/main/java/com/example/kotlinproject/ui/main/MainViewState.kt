package com.example.kotlinproject.ui.main

import com.example.kotlinproject.data.model.Note

// Класс представлять состояние view и служить для передачи данных из ViewModel во view.
class MainViewState (val notes: List<Note>? = null, error : Throwable? = null)
    :BaseViewState<List<Note>?>(notes, error){  // Содержит список заметок
}