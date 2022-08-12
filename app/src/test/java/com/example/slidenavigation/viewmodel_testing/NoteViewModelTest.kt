package com.example.slidenavigation.viewmodel_testing


import com.example.slidenavigation.mvvm.NoteRepository
import com.example.slidenavigation.mvvm.NoteViewModel
import com.example.slidenavigation.room.Note
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class NoteViewModelTest {

    @MockK
    var list = mutableListOf<Note>()

    @MockK
    lateinit var noteRepository: NoteRepository

    lateinit var noteViewModel: NoteViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val flow = MutableStateFlow(list)
        coEvery {
            noteRepository.allNotes
        }returns flow
        noteViewModel = NoteViewModel(noteRepository)
    }

    val note = Note(
        "task", "hello",
        "may"
    )

    @Test
    fun insertNote() {
        coEvery {
            noteRepository.insert(note)
        } returns Unit

        runBlocking {
            noteViewModel.addNote(note = note)
        }

        coVerify {
            noteRepository.insert(note)
        }
    }
    @Test
    fun deleteNote() {
        coEvery {
            noteRepository.delete(note)
        } returns Unit

        runBlocking {
            noteViewModel.deleteNote(note = note)
        }

        coVerify {
            noteRepository.delete(note)
        }
    }
    @Test
    fun updateNote() {
        coEvery {
            noteRepository.update(note)
        } returns Unit

        runBlocking {
            noteViewModel.updateNote(note = note)
        }

        coVerify {
            noteRepository.update(note)
        }
    }
}