package com.example.notebook.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title") var noteTitle: String,
    @ColumnInfo(name = "description") val noteDescription: String,
    @ColumnInfo(name = "dateAdded") val dateAdded: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}