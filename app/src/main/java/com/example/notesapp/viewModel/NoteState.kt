package com.example.notesapp.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesapp.model.Notes

class NoteState {
    var notes: List<Notes> = emptyList()
    var title: MutableState<String> = mutableStateOf("")
    var description: MutableState<String> = mutableStateOf("")

}