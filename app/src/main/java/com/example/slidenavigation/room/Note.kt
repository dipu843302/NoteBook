package com.example.slidenavigation.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "notesTable")
data class Note(
    @ColumnInfo(name = "title") var noteTitle: String,
    @ColumnInfo(name = "description") val noteDescription: String,
    @ColumnInfo(name = "dateAdded") val dateAdded: String,
):Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}