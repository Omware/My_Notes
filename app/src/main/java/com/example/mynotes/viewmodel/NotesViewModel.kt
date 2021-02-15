package com.example.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.database.NotesDao
import com.example.mynotes.database.NotesDatabase
import com.example.mynotes.model.Note
import com.example.mynotes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val getAllNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val notesDao = NotesDatabase.getInstance(application).notesDao
        repository = NoteRepository(notesDao)
        getAllNotes = repository.getAllNotes
    }

    /* insert note */
    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    /* update note */
    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    /* delete note */
    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    /* delete all notes */
    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }
}