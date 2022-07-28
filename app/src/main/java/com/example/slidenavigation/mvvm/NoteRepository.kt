package com.example.notebook.mvvm

import androidx.lifecycle.LiveData
import com.example.notebook.room.Note
import com.example.notebook.room.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: Flow<List<Note>> =notesDao.getAllNotes()

    suspend fun insert(note:Note){
        notesDao.insert(note)
    }

    suspend fun delete(note:Note){
        notesDao.delete(note)
    }

    suspend fun update(note:Note){
        notesDao.update(note)
    }
}