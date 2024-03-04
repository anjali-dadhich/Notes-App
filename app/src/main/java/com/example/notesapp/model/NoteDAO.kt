package com.example.notesapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDAO {
    @Upsert
    suspend fun updateAndInsertNote(note : Notes )

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM Notes ORDER BY date")
    fun getNotes() : Flow<List<Notes>>
    @Query("SELECT * FROM Notes WHERE notes.id=:id")
    suspend fun getNoteById (id : Int) : Notes?
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<Notes>>
}