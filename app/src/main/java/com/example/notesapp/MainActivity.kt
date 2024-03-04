package com.example.notesapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.NoteRoomDatabase
import com.example.notesapp.screens.AddNotesScreen
import com.example.notesapp.screens.NotesDisplayScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewModel.NoteState
import com.example.notesapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteRoomDatabase :: class.java,
            "notes.db"
        ).build()
    }
    private val viewModel by viewModels<NoteViewModel> {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(database.doa) as T
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Scaffold (modifier = Modifier.fillMaxSize( ) ){
                    val state  by viewModel.state.collectAsState()
                    val navController : NavHostController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NotesDisplayScreen") {
                        composable("NotesDisplayScreen"){
                            NotesDisplayScreen(state = state as NoteState, navController = navController, onEvent = viewModel :: onEvent)
                        }
                        composable("AddNotesScreen"){
                            AddNotesScreen(state = state as NoteState, navController = navController, onEvent = viewModel :: onEvent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}