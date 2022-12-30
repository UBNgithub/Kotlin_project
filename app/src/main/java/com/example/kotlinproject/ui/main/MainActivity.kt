package com.example.kotlinproject.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.databinding.ActivityMainBinding
import com.example.kotlinproject.viewmodel.MainViewModel

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var ui: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter
    //lateinit var toolbar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(ui.root)  // для binding


        //toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)
        setSupportActionBar(ui.toolbar) //// Через View Binding

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })
        ui.mainRecycler.adapter = adapter // Через View Binding
        ui.floatingActionButton.setOnClickListener { openNoteScreen(null) }

        viewModel.viewState().observe(this, Observer<MainViewState> { it ->
            it?.let { adapter.notes = it.notes }
        })


//
//        val button = findViewById<Button>(R.id.button)
//        val  textView = findViewById<TextView>(R.id.textView)
//
//        button.setOnClickListener { it
//            (it as Button).text = "GHHHHHHHH"
//            textView.text = "JJJJJJJJ"
//        }
    }

    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note)
        startActivity(intent)
    }
}