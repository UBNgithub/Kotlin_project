package com.example.kotlinproject.data

import com.example.kotlinproject.data.model.Note

//класс синглтон
object Repository {

    private  val notes: List<Note>

    init {
        notes = listOf(
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
            Note("Моя первая заметка",
                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
        )
    }



    fun getNotes(): List<Note>{
        return notes
    }

}