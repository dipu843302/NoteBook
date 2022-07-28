package com.example.notebook.mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.room.Note
import com.example.notebook.room.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {

     val allNote:Flow<List<Note>>
    private val repository:NoteRepository

    init {
        val dao=NoteDatabase.getDatabase(application).getNotesDao()
        repository= NoteRepository(dao)
        allNote=repository.allNotes
    }

    fun deleteNote(note:Note)=viewModelScope.launch (Dispatchers.IO){
        repository.delete(note)
    }

    fun updateNote(note:Note)=viewModelScope.launch (Dispatchers.IO){
        repository.update(note)
    }
    fun addNote(note:Note)=viewModelScope.launch (Dispatchers.IO){
        repository.insert(note)
    }

}