package com.example.kotlinproject.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.data.error.NoAuthException
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import com.example.kotlinproject.data.model.User
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"
private const val USER_COLLECTION = "users"

class FireStoreProvider : RemoteDataProvider {
    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName}"
    }

    //база данных
    private val db = FirebaseFirestore.getInstance()

    //ссылка на коллекцию
    // private val noteReference = db.collection(NOTES_COLLECTION)
    // Получение ссылки на коллекцию заметок пользователя (замена NoteReference)
    private fun getUserNoteCollection() = currentUser?.let {
        db.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    //Авторизованный юзер
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser


    //    override fun subscribeToAllNotes(): LiveData<NoteResult> {
//     val result = MutableLiveData<NoteResult>()
//        noteReference.addSnapshotListener(object  : EventListener<QuerySnapshot>{
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//            if (error != null){
//                result.value = NoteResult.Error(error)
//            } else if (value != null){
//                val notes = mutableListOf<Note>()
//                for (doc: QueryDocumentSnapshot in value){
//                    notes.add(doc.toObject(Note::class.java))
//                }
//                result.value = NoteResult.Success(notes)
//            }
//            }
//        })
//        return  result
//    }
    // Рефакторим метод сверху subscribeToAllNotes()
//    override fun subscribeToAllNotes(): LiveData<NoteResult> =
//        MutableLiveData<NoteResult>().apply {
//            noteReference.addSnapshotListener { snapshot, error ->
//                value = error?.let { NoteResult.Error(it) }
//                    ?: snapshot?.let {
//                        val notes = it.documents.map {  it.toObject(Note::class.java)}
//                        NoteResult.Success(notes)
//                    }
//            }
//        }
    //рефакторим subscribeToAllNotes() под юзера
    override fun subscribeToAllNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNoteCollection().addSnapshotListener { snapshot, error ->
                    value = error?.let { throw it }
                        ?: snapshot?.let {
                            val notes = it.documents.map { it.toObject(Note::class.java) }
                            NoteResult.Success(notes)
                        }
                }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }


    // получение
//    override fun getNoteById(id: String): LiveData<NoteResult> {
//        val result = MutableLiveData<NoteResult>()
//        noteReference.document(id)
//            .get()
//            .addOnSuccessListener { p0 ->
//                result.value = NoteResult.Success(p0?.toObject(Note::class.java))
//            }
//            .addOnFailureListener {
//                result.value = NoteResult.Error(it)
//            }
//        return result
//    }

    //Рефакторим getNoteById
    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {

            try {
                getUserNoteCollection().document(id)
                    .get()
                    .addOnSuccessListener { p0 ->
                        value = NoteResult.Success(p0?.toObject(Note::class.java))
                    }
                    .addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    // Отправляет документ на сервер, сохраняет, подписывается на изменения
//    override fun saveNote(note: Note): LiveData<NoteResult> {
//        val result = MutableLiveData<NoteResult>()
//
//        noteReference.document(note.id)
//            .set(note)
//            .addOnSuccessListener {
//                Log.d(TAG, "Note $note is saved")
//                result.value = NoteResult.Success(note)
//            }.addOnFailureListener {
//                OnFailureListener { p0 ->
//                    Log.d(TAG, "Error saving note  $note , message: ${p0.message}")
//                    result.value = NoteResult.Error(p0)
//                }
//            }
//        return result
//    }
    // Рефакторим saveNote
    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNoteCollection().document(note.id)
                    .set(note)
                    .addOnSuccessListener {
                        Log.d(TAG, "Note $note is saved")
                        value = NoteResult.Success(note)
                    }.addOnFailureListener {
                        OnFailureListener { p0 ->
                            Log.d(TAG, "Error saving note  $note , message: ${p0.message}")
                            //value = NoteResult.Error(p0)
                            throw p0
                        }
                    }

            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        }

    override fun deleteNote(noteId: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNoteCollection().document(noteId)
                    .delete()
                    .addOnSuccessListener {
                        value = NoteResult.Success(null)
                    }
                    .addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (e: Throwable) {
                value= NoteResult.Error(e)

            }
        }


}