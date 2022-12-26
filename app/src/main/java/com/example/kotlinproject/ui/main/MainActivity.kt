package com.example.kotlinproject.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivityMainBinding
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
        adapter = MainAdapter()
       ui.mainRecycler.adapter = adapter // Через View Binding

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
}