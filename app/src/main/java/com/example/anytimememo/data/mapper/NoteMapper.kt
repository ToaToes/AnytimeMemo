package com.example.anytimememo.data.mapper

import com.example.anytimememo.data.local.entity.NoteEntity
import com.example.anytimememo.domain.model.Note

fun NoteEntity.asExternalModel(): Note = Note (
    id, title, content, date
)

fun Note.toEntity(): NoteEntity = NoteEntity (
    id, title, content, date
)