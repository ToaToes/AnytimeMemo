package com.example.anytimememo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.anytimememo.data.local.dao.NoteDao
import com.example.anytimememo.data.local.entity.NoteEntity


@Database(
    version = 1,
    entities = [NoteEntity::class]
)

abstract class NoteDatabase: RoomDatabase() {

    abstract val dao: NoteDao

    //database name
    companion object {
        const val DATABASE_NAME = "notes_db"
    }


}