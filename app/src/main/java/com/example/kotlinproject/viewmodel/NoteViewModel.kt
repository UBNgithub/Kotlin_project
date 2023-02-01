package com.example.kotlinproject.viewmodel


import com.example.kotlinproject.data.Repository
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import com.example.kotlinproject.ui.main.NoteViewState

class NoteViewModel(val repository: Repository = Repository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote : Note?
    get() = viewStateLiveData.value?.data?.note

    private var pendingNote: Note? = null
    fun saveChange(note: Note) {
        //pendingNote = note
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note =note))
    }

    override fun onCleared() {
//        if (pendingNote != null) {
//            repository.saveNote(pendingNote!!)
//        }
        currentNote?.let { repository.saveNote(it) }
    }

//    fun loadNote(noteId: String) {
//        repository.getNoteById(noteId).observeForever(Observer<NoteResult> { t ->
//            if (t == null)
//                return@Observer
//            when (t) {
//                is NoteResult.Success<*> ->
//                    viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = t.data as? Note))
//                is NoteResult.Error ->
//                    viewStateLiveData.value = NoteViewState(error = t.error)
//            }
//        })
//    }
    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { t ->
           t?.let {
               viewStateLiveData.value = when (t){
                   is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = t.data as? Note))
                   is NoteResult.Error -> NoteViewState(error = t.error)
               }
           }

        }
    }





    fun deleteNote() {
       currentNote?.let {
           repository.deleteNote(it.id).observeForever { result->
               result?.let { noteResult ->
               viewStateLiveData.value = when (noteResult){
                   is NoteResult.Success<*> ->
                       NoteViewState(NoteViewState.Data(isDeleted = true))
                   is NoteResult.Error ->
                       NoteViewState(error = noteResult.error)
               }

               }
           }
       }
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