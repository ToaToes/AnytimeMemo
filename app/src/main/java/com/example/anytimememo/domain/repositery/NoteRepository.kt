package com.example.anytimememo.domain.repositery

import com.example.anytimememo.domain.model.Note
// it gives you this error because Java concurrent Flow class take 0 type arguments, but Coroutines Flow take one
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun  updateNote(note: Note)
}