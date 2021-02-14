package com.example.mynotes.repository

import androidx.lifecycle.LiveData
import com.example.mynotes.database.NotesDao
import com.example.mynotes.model.Note

class NoteRepository(private val notesDao: NotesDao) {

    val getAllNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    // insert note
    suspend fun insertNote(note: Note) = notesDao.insertNote(note)

    // update note
    suspend fun updateNote(note: Note) = notesDao.updateNote(note)

    // delete note
    suspend fun deleteNote(note: Note) = notesDao.deleteNote(note)

    // delete all notes
    suspend fun deleteAllNotes() = notesDao.deleteAllNotes()

}