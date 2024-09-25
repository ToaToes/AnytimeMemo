package com.example.anytimememo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.anytimememo.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow


//Data access object, perform on database
@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM NoteEntity
        WHERE id = :id
    """)
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
}