package com.example.kotlinproject.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class LogoutDialog  : DialogFragment(){
    companion object{
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance()= LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Выйти из аккаунта?")
            .setMessage("Все ваши заметки будут сохранены. Вы сможете получить к ним доступ после авторизации.")
            .setPositiveButton("Ок"){ _,_ ->(activity as LogoutListener).onLogout()}
            .setNegativeButton("Отмена"){_,_ -> dismiss()}.create()

    interface LogoutListener{
        fun onLogout()
    }
}