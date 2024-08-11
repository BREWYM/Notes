package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.lifecycle.ViewModelProvider
import com.example.notes.ui.theme.NotesTheme

class MainActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApp(noteViewModel = noteViewModel)

        }
    }
}