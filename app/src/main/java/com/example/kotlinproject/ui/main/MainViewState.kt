package com.example.kotlinproject.ui.main

import com.example.kotlinproject.data.model.Note

// Класс представлять состояние view и служить для передачи данных из ViewModel во view.
data class MainViewState (val notes: List<Note>){  // Содержит список заметок
}