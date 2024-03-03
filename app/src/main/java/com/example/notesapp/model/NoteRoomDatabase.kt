package com.example.notesapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Notes :: class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract val doa : NoteDAO
}