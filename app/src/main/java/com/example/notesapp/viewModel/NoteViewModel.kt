package com.example.notesapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.notesapp.model.NoteDAO
import com.example.notesapp.model.Notes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel (private var dao : NoteDAO) : ViewModel() {

    private var isShortedByDate = MutableStateFlow(true)

    var notesList = dao.getNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var _state = MutableStateFlow(NoteState())
    var state : StateFlow<NoteState> = combine(_state, notesList ) {
        state,notesList ->
        state.copy(
            notesList = notesList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NoteState())

    fun onEvent(event : NoteEvent){
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            is NoteEvent.SaveNote -> {
                val note = Notes(
                    title = state.value.title.value,
                    description = state.value.description.value ,
                    date = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.updateAndInsertNote(note)
                }
            }
            is NoteEvent.UpdateNote -> {
                val note = Notes(
                    id = state.value.id.value,
                    title = state.value.title.value,
                    description = state.value.description.value,
                    date = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.updateAndInsertNote(note)
                }
            }
            is NoteEvent.SearchNote -> {
                viewModelScope.launch {
                    dao.searchNotes(state.value.query.value)
                }
            }
        }
    }
}