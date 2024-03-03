package com.example.notesapp.viewModel

import com.example.notesapp.model.Notes

sealed interface NoteEvent {
        data class DeleteNote(var note : Notes) : NoteEvent
    data class SaveNote(var title : String, var description : String) : NoteEvent

}