package com.example.kotlinproject.data.model
// Для примера делаем класс sealed
sealed class NoteResult {
    data class Success<out T> (val data: T ): NoteResult()
    data class Error(val error: Throwable) : NoteResult()
}