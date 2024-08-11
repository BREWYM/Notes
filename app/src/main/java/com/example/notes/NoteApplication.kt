package com.example.notes

import android.app.Application
import androidx.room.Room

class NoteApplication: Application() {
    private val database by lazy{
        Room.databaseBuilder(this, NoteDataBase::class.java, "note_database").build()
    }
    val repository by lazy {
        NoteRepository(database.noteDao())
    }
}