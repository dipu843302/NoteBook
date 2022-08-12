package com.example.slidenavigation.roomdb_testing

import android.content.Context
import com.example.slidenavigation.room.NoteDatabase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Test

class RoomDBTesting {

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var noteDatabase: NoteDatabase

    @Before
    fun set(){
        MockKAnnotations.init(this)
    }

    @Test
    fun getNoteDataBase_equalityCheck(){
        mockkObject(NoteDatabase)
        every {
            NoteDatabase.getDatabase(context)
        }returns noteDatabase

        val result=NoteDatabase.getDatabase(context)
        assertThat(result).isEqualTo(noteDatabase)
    }

    @Test
    fun getNoteDataBase_NullCheck(){
        mockkObject(NoteDatabase)
        every {
            NoteDatabase.getDatabase(context)
        }returns noteDatabase

        val result=NoteDatabase.getDatabase(context)
        assertThat(result).isNotNull()
    }
}