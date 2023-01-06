package com.example.kotlinproject.ui.main


import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinproject.R

import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<List<Note>?, MainViewState>() {
    override val viewModel: MainViewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_main
    private  lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(note: Note) {
              openNoteScreen(note)
            }
        })
        mainRecycler.adapter =adapter
        floatingActionButton.setOnClickListener {
            openNoteScreen(null)
        }


    }
    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }


}
//class MainActivity : AppCompatActivity() {
//    lateinit var ui: ActivityMainBinding
//    lateinit var viewModel: MainViewModel
//    lateinit var adapter: MainAdapter
//    //lateinit var toolbar: androidx.appcompat.widget.Toolbar
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ui = ActivityMainBinding.inflate(layoutInflater)
//        //setContentView(R.layout.activity_main)
//        setContentView(ui.root)  // для binding
//
//
//        //toolbar = findViewById(R.id.toolbar)
//        //setSupportActionBar(toolbar)
//        setSupportActionBar(ui.toolbar) //// Через View Binding
//
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
//            override fun onItemClick(note: Note) {
//                openNoteScreen(note)
//            }
//        })
//        ui.mainRecycler.adapter = adapter // Через View Binding
//        ui.floatingActionButton.setOnClickListener { openNoteScreen(null) }
//
//        viewModel.viewState().observe(this, Observer<MainViewState> { it ->
//            it?.let { adapter.notes = it.notes }
//        })
//    }
//
//    private fun openNoteScreen(note: Note?) {
//        val intent = NoteActivity.getStartIntent(this, note)
//        startActivity(intent)
//    }
//}