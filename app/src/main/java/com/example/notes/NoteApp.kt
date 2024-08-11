package com.example.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp(noteViewModel: NoteViewModel = viewModel()) {
    val notes by noteViewModel.allNotes.observeAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Notes") }) },
        content = {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .padding(top = 40.dp)
            ) {
                NoteList(notes = notes, onDeleteNote = { noteViewModel.deleteNote(it) })
                Spacer(modifier = Modifier.height(16.dp))
                NoteInput(onAddNote = { title, content ->
                    noteViewModel.addNote(Note(title = title, content = content))
                })
            }
        }
    )
}


@Composable
fun NoteList(notes: List<Note>, onDeleteNote: (Note) -> Unit) {
    LazyColumn {
        items(notes) { note ->
            NoteItem(note = note, onDeleteNote = onDeleteNote)
        }
    }
}

@Composable
fun NoteItem(note: Note, onDeleteNote: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.title, style = MaterialTheme.typography.headlineSmall)
            Text(note.content, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = { onDeleteNote(note) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun NoteInput(onAddNote: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onAddNote(title, content)
            title = ""
            content = ""
        }) {
            Text("Add Note")
        }
    }
}