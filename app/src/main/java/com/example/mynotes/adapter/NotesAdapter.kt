package com.example.mynotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.model.Note
import com.example.mynotes.ui.fragments.NotesFragmentDirections
import kotlinx.android.synthetic.main.notes_lyt.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_lyt, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = notes[position]

        holder.itemView.title_text.text = currentNote.title
        holder.itemView.desc_text.text = currentNote.description

        // set clicklistener for each itemview
        holder.itemView.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToUpdateFragment(currentNote)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(note: List<Note>) {
        this.notes = note
        notifyDataSetChanged()
    }

}