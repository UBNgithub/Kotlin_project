package com.example.kotlinproject.data.provider

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.data.model.NoteResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import java.lang.Exception

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {
    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName}"
    }

    //база данных
    private val db = FirebaseFirestore.getInstance()

    //ссылка на коллекцию
    private val noteReference = db.collection(NOTES_COLLECTION)


    override fun subscribeToAllNotes(): LiveData<NoteResult> {
     val result = MutableLiveData<NoteResult>()
        noteReference.addSnapshotListener(object  : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
            if (error != null){
                result.value = NoteResult.Error(error)
            } else if (value != null){
                val notes = mutableListOf<Note>()
                for (doc: QueryDocumentSnapshot in value){
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = NoteResult.Success(notes)
            }
            }
        })

        return  result
    }

    // получение
    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        noteReference.document(id)
            .get()
            .addOnSuccessListener { p0 ->
                result.value = NoteResult.Success(p0?.toObject(Note::class.java))
            }
            .addOnFailureListener{
                result.value =NoteResult.Error(it)
            }
        return result
    }

    // Отправляет документ на сервер, сохраняет, подписывается на изменения
    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        noteReference.document(note.id)
            .set(note)
            .addOnSuccessListener {
                Log.d(TAG, "Note $note is saved")
                result.value = NoteResult.Success(note)
            }
//        .addOnFailureListener{object : OnFailureListener{
//            override fun onFailure(p0: Exception) {
//                Log.d(TAG,"Error saving note  $note , message: ${p0.message}")
//
//                result.value = NoteResult.Error(p0)
//            }
//        }
            //Тоже через лямбду
            .addOnFailureListener {
                OnFailureListener { p0 ->
                    Log.d(TAG, "Error saving note  $note , message: ${p0.message}")
                    result.value = NoteResult.Error(p0)
                }
            }
        return result
    }
}