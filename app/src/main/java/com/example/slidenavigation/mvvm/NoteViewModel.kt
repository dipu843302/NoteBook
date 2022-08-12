package com.example.slidenavigation.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.room.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository):ViewModel() {

     val allNote:Flow<List<Note>> = repository.allNotes

    fun deleteNote(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.delete(note)
    }

    fun updateNote(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.update(note)
    }
    fun addNote(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.insert(note)
    }

}