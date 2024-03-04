package com.example.notesapp.viewModel

import com.example.notesapp.model.Notes

sealed interface NoteEvent {
        data class DeleteNote(var note : Notes) : NoteEvent
    data class SaveNote(var title : String, var description : String) : NoteEvent

    data class UpdateNote(var id: Int, var title: String, var description: String) : NoteEvent


    data class SearchNote (var query: String) : NoteEvent

}