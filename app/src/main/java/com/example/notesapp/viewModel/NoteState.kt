package com.example.notesapp.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesapp.model.Notes

class NoteState (
    var notesList: List<Notes> = emptyList(),
    var title: MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    var query: MutableState<String> = mutableStateOf(""),
    var id : MutableState<Int> = mutableStateOf(0)

) {
    fun copy(notesList: List<Notes>): NoteState {
        return NoteState(
            notesList = notesList,
            // Copy other properties as needed
        )
    }
}