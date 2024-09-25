package com.example.anytimememo.data.repositery

import com.example.anytimememo.data.local.dao.NoteDao
import com.example.anytimememo.data.local.entity.NoteEntity
import com.example.anytimememo.data.mapper.asExternalModel
import com.example.anytimememo.data.mapper.toEntity
import com.example.anytimememo.domain.model.Note
import com.example.anytimememo.domain.repositery.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImplementation(
    private val dao: NoteDao
): NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
            .map { notes ->
                notes.map{
                    it.asExternalModel()
                }
            }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.asExternalModel()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }
}