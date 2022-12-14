package com.example.slidenavigation.room

import androidx.room.*
import com.example.slidenavigation.room.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notesTable")
    fun getAllNotes(): Flow<List<Note>>


}