package com.shashi.notesappmvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val context: Context, val listner: INoteAdapterListner) :
    RecyclerView.Adapter<NoteViewHolder>() {

    val notes: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val holder = NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
        return holder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.textViewNote.text = note.text

        holder.imageViewDelete.setOnClickListener {
            listner.onItemClick(notes[position])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(updatedNotes: ArrayList<Note>) {
        notes.clear()
        notes.addAll(updatedNotes)
        notifyDataSetChanged()
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewNote: TextView = itemView.findViewById(R.id.text_view_note)
    val imageViewDelete: ImageView = itemView.findViewById(R.id.image_view_delete)
}

interface INoteAdapterListner {
    fun onItemClick(note: Note)
}