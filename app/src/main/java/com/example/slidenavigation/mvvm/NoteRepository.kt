package com.example.slidenavigation.mvvm

import com.example.slidenavigation.room.Note
import com.example.slidenavigation.room.NotesDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: Flow<List<Note>> =notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }
}