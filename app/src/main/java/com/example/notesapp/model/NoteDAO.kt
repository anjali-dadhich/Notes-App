package com.example.notesapp.model

import androidx.room.Delete
import androidx.room.Upsert

interface NoteDAO {
    @Upsert
    suspend fun updateAndInsertNote(note : Notes )

    @Delete
    suspend fun deleteNote(note: Notes)
}