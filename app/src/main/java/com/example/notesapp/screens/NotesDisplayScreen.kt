package com.example.notesapp.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.viewModel.NoteEvent
import com.example.notesapp.viewModel.NoteState

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NotesDisplayScreen(
    state : NoteState,
    navController: NavController,
    onEvent: (NoteEvent) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeholder = { Text("Search", style = TextStyle(color = Color.LightGray)) },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),

                    )
                },
                actions = {
                    SearchIconButton(
                        onClick = { /* Perform search action */ }
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Gray,
                onClick = {
                    state.title.value = ""
                    state.description.value = ""
                    navController.navigate("AddNotesScreen")
                }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.notesList.size) {
                NoteItem(
                    state = state,
                    index = it,
                    onEvent = onEvent,
                    navController
                )
            }
        }
    }
}

@Composable
fun SearchIconButton(onClick: () -> Unit) {

}

@Composable
fun NoteItem(state: NoteState, index: Int,onEvent: (NoteEvent) -> Unit, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirmed = {
                onEvent(NoteEvent.DeleteNote(state.notesList[index]))
                showDialog = false
            },
            onCancel = {
                showDialog = false
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .padding(10.dp)
    ) {
        Column (modifier = Modifier.weight(3f)) {
            Text(text = state.notesList.get(index).title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = state.notesList.get(index).description,
                fontSize = 12.sp,
                color = Color.White)
        }
        Column (modifier = Modifier.weight(1f)) {
            IconButton(onClick = {
                    state.title.value = state.notesList.get(index).title
                    state.description.value = state.notesList.get(index).description
                    state.id.value = state.notesList.get(index).id
                    navController.navigate("AddNotesScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null )
            }
            Spacer(modifier = Modifier.height(4.dp))
            IconButton(onClick = {
                showDialog = true
                /*onEvent(NoteEvent.DeleteNote(
                    state.notesList.get(index=index)
                ))*/
            }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = null )
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirmed: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = "Confirm Delete") },
        text = { Text("Are you sure you want to delete?") },
        confirmButton = {
            Button(
                onClick = {
                    onDeleteConfirmed()
                    onCancel()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text("Cancel")
            }
        }
    )
}
