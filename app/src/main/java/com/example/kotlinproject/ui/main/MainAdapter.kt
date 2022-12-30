package com.example.kotlinproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.R
import com.example.kotlinproject.data.model.Color
import com.example.kotlinproject.data.model.Note

class MainAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

    var notes : List <Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val  inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int): Unit {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return  notes.size
    }
   inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val body = itemView.findViewById<TextView>(R.id.body)

        fun bind (note: Note){
            title.text = note.title
            body.text = note.note
            // itemView.setBackgroundColor(note.color)
            val color = when (note.color) { Color.WHITE -> R.color.color_white
                Color.VIOLET ->R.color.color_violet
                Color.YELLOW ->R.color.color_yellow
                Color.RED ->R.color.color_red
                Color.PINK ->R.color.color_pink
                Color.GREEN ->R.color.color_green
                Color.BLUE ->R.color.color_blue
            }
            itemView.setBackgroundResource(color)
            itemView.setOnClickListener{onItemClickListener.onItemClick(note)}

        }


    }
    interface  OnItemClickListener{
        fun onItemClick(note: Note)
    }

}


