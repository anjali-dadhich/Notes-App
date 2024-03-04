package com.example.notesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.viewModel.NoteEvent
import com.example.notesapp.viewModel.NoteState

@Preview
@Composable
fun AddNotesScreen (
    state : NoteState,
    navController: NavController,
    onEvent: (NoteEvent) -> Unit,
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (state.id.value == 0) {
                    onEvent(
                        NoteEvent.SaveNote(
                            title = state.title.value,
                            description = state.description.value
                        )
                    )
                } else {
                    onEvent(
                        NoteEvent.UpdateNote(
                            id = state.id.value,
                            title = state.title.value,
                            description = state.description.value
                        )
                    )
                }

                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null )
            }
        }
    ) {
            Column (
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                TextField(value = state.title.value, onValueChange = {
                    state.title.value = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Title")
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                )

                TextField(value = state.description.value, onValueChange = {
                    state.description.value = it
                },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Description")
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            }
    }
}