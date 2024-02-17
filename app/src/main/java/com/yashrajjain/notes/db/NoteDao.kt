package com.yashrajjain.notes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yashrajjain.notes.model.Note
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)
    @Delete
    suspend fun deleteNote(note:Note)
    @Update
    suspend fun updateNote(note:Note)
    @Query("SELECt * from note order by id")
    fun getAllNotes():LiveData<List<Note>>

    @Query("SELECT * from note where noteTitle like :query or noteDesc like :query")
    fun searchNote(query:String):LiveData<List<Note>>
}