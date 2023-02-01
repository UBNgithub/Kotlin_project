package com.example.kotlinproject.ui.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.example.kotlinproject.R
import com.example.kotlinproject.data.model.Color
import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.databinding.ActivityNoteBinding
import com.example.kotlinproject.extensions.DATE_TIME_FORMAT
import com.example.kotlinproject.extensions.format
import com.example.kotlinproject.extensions.getColorInt
import com.example.kotlinproject.viewmodel.BaseViewModel
import com.example.kotlinproject.viewmodel.NoteViewModel
import com.google.android.material.textfield.TextInputEditText

//import kotlinx.android.synthetic.main.activity_main.toolbar
//
//import kotlinx.android.synthetic.main.activity_note.*
//import kotlinx.android.synthetic.main.item_note.*
import java.text.SimpleDateFormat
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.Note"
        fun getStartIntent(context: Context, noteId: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    private var color: Color = Color.RED
    private var note: Note? = null

    // private lateinit var ui : ActivityNoteBinding
    // private lateinit var viewModel: NoteViewModel
    override val viewModel: NoteViewModel by lazy { ViewModelProvider(this).get(NoteViewModel::class.java) }
    override val layoutRes: Int = R.layout.activity_note
    override val ui: ActivityNoteBinding
       by lazy { ActivityNoteBinding.inflate(layoutInflater) }

//
//   private  lateinit var editText: TextInputEditText
//   private  lateinit var textView: EditText

    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            triggerSaveNote()
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // не используем
        }

        override fun afterTextChanged(s: Editable?) {
            // не используем
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ui = ActivityNoteBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_note)
        // setContentView(ui.root)


        //  viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // note = intent.getParcelableExtra(EXTRA_NOTE)
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        // Log.d("ONCREATE ", note.toString())
        setSupportActionBar(ui.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let {
            viewModel.loadNote(it)
        }

//        supportActionBar?.title = if (noteId != null) {
//            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
//        } else {
//            getString(R.string.new_note_title)
//        }
        //initView()
        setEditListener()
    }

    private fun initView() {
//        if (note != null) {
//            title_ET.setText(note?.title ?: "")
//            bodyET.setText(note?.note ?: "")
////
//            val color = when (note!!.color) {
//                Color.WHITE -> R.color.color_white
//                Color.VIOLET -> R.color.color_violet
//                Color.YELLOW -> R.color.color_yellow
//                Color.RED -> R.color.color_red
//                Color.PINK -> R.color.color_pink
//                Color.GREEN -> R.color.color_green
//                Color.BLUE -> R.color.color_blue
//            }
//            toolbar.setBackgroundColor(resources.getColor(color))
        note?.run {
            removeEditListener()
//            ui.titleET.setText(title)
//            ui.bodyET.setText(note)
            if (title != ui.titleET.text.toString())
                ui.titleET.setText(title)
            if (note != ui.bodyET.text.toString())
                ui.titleET.setText(note)

            setEditListener()
            ui.toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))
            supportActionBar?.title = lastChanged.format()
        }
        if (note == null){
            supportActionBar?.title = getString(R.string.new_note_title)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =

        when (item.itemId) {
            android.R.id.home -> onBackPressed().let {  true}
            R.id.palette_item -> togglePalette().let{true}
            R.id.delete_item -> deleteNote().let{true}
            else -> super.onOptionsItemSelected(item)
        }

    private fun deleteNote(){
        Log.d("NoteActivity", "deleteNote()")
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_dialog_message))
            .setNegativeButton(R.string.cancel_btn_title){ dialog, _ ->dialog.dismiss() }
            .setPositiveButton(getString(R.string.ok_btn_title)){ _, _ -> viewModel.deleteNote()}
            .show()

    }

    private fun togglePalette() {
    }



    private fun triggerSaveNote() {
        Log.d("triggerSaveNote", "Тригер")
        if (ui.titleET.text == null || ui.titleET.text!!.length < 3) return
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                Log.d("triggerSaveNote", "run")
                note = note?.copy(
                    title = ui.titleET.text.toString(),
                    note = ui.bodyET.text.toString(),
                    lastChanged = Date()
                ) ?: createNewNote()
                if (note != null) {
                    Log.d("triggerSaveNote", "note != null")
                } else
                    Log.d("triggerSaveNote", "note = null")


                if (note != null) viewModel.saveChange(note!!)
            }


        }, SAVE_DELAY)
    }
//    private  fun createNewNote() : Note = Note(UUID.randomUUID().toString(),
//        title_ET.text.toString(),
//    bodyET.text.toString())

    private fun createNewNote(): Note {
        return Note(
            UUID.randomUUID().toString(),
            ui.titleET.text.toString(),
            ui.bodyET.text.toString()
        )
        Log.d("createNewNote", ui.titleET.text.toString() + " " + ui.bodyET.text.toString())
        Log.d("createNewNote", "Заметка!!!")
    }

//    override fun renderData(data: Note?) {
//        this.note = data
//        initView()
//    }

        override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()
       
       this.note= data.note
       data.note?.let { color = it.color }     
        initView()
    }
    private  fun setEditListener(){
        ui.titleET.addTextChangedListener(textChangeListener)
        ui.bodyET.addTextChangedListener(textChangeListener)
    }

    private  fun removeEditListener(){
        ui.titleET.removeTextChangedListener(textChangeListener)
        ui.bodyET.removeTextChangedListener(textChangeListener)
    }



}
