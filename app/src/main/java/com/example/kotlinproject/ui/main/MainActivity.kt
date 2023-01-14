package com.example.kotlinproject.ui.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.CookieSyncManager.createInstance
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.kotlinproject.R

import com.example.kotlinproject.data.model.Note
import com.example.kotlinproject.databinding.ActivityMainBinding
import com.example.kotlinproject.ui.main.splash.SplashActivity
import com.example.kotlinproject.viewmodel.MainViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {
    companion object {
        fun getStartIntent(context: Context) =
            Intent(context,MainActivity::class.java)
    }

    override val viewModel: MainViewModel
    by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val ui: ActivityMainBinding
    by lazy { ActivityMainBinding.inflate(layoutInflater) }


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
        ui.mainRecycler.adapter =adapter
        ui.floatingActionButton.setOnClickListener {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
           R.id.logout -> showLogoDialog().let{true}
           else -> false
        }

    private fun showLogoDialog(){
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
        LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)

    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener{
                startActivity((Intent(this,SplashActivity::class.java)))
                finish()
            }
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