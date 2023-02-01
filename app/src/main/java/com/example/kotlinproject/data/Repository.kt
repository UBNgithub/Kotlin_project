package com.example.kotlinproject.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.data.model.Color
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.provider.FireStoreProvider
import com.example.kotlinproject.data.provider.RemoteDataProvider
import java.util.*

//класс синглтон (object)
object Repository {
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()
    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)


    //private  val notes: List<Note>

    //    init {
//        notes = listOf(
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//            Note("Моя первая заметка",
//                "Kotlin очень краткий, но при этом выразительный язык", 0xfff06292.toInt()),
//        )
//    }


//    private  val notesLiveData = MutableLiveData<List<Note>>()
//    private val notes: MutableList<Note> = mutableListOf(
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.WHITE
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.GREEN
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.BLUE
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.PINK
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.YELLOW
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.RED
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.BLUE
//        ),
//        Note(
//            id = UUID.randomUUID().toString(),
//            title = "Моя первая заметка",
//            note = "Kotlin очень краткий, но при этом выразительный язык",
//            color = Color.WHITE
//        )
//
//    )
//    init {
//        notesLiveData.value = notes
//    }
//
////
////    fun getNotes(): List<Note> {
////        return notes
////    }
//    fun getNotes(): LiveData<List<Note>>{
//        return notesLiveData
//    }
//    fun saveNote(note: Note){
//        addOrReplace(note)
//        notesLiveData.value = notes
//    }
//
//    private fun addOrReplace(note: Note) {
//        for (i in 0 until  notes.size){
//            if (notes[i] == note){
//                notes[i] = note
//                return
//            }
//        }
//        notes.add(note)
//
//    }

}