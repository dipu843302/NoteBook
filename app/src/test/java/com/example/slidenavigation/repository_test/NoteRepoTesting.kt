package com.example.slidenavigation.repository_test

import com.example.slidenavigation.mvvm.NoteRepository
import com.example.slidenavigation.room.Note
import com.example.slidenavigation.room.NotesDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NoteRepoTesting {
    @MockK
    var list = mutableListOf<Note>()

    @MockK
    lateinit var noteDoa: NotesDao

    lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val flow = MutableStateFlow(list)
        coEvery {
            noteDoa.getAllNotes()
        }returns flow
        noteRepository = NoteRepository(noteDoa)
    }
    val note = Note(
        "task", "hello",
        "may"
    )
    @Test
    fun insertNote(){

        coEvery {
            noteDoa.insert(note)
        }returns Unit

        runBlocking {
            noteRepository.insert(note)
        }
        coVerify {
            noteDoa.insert(note)
        }
    }

    @Test
    fun updateNote(){
        coEvery {
            noteDoa.update(note)
        }returns Unit

        runBlocking {
            noteRepository.update(note)
        }
        coVerify {
            noteDoa.update(note)
        }
    }

    @Test
    fun deleteNote() {
        // Stub Calls
        coEvery {
            noteDoa.delete(note)
        } returns Unit
        //Calling function for testing
        runBlocking {
            noteRepository.delete(note)
        }
        // verifying the function
        coVerify {
            noteDoa.delete(note)
        }
    }


}