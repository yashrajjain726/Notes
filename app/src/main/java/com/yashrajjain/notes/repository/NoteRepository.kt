package com.yashrajjain.notes.repository

import com.yashrajjain.notes.db.NoteDatabase
import com.yashrajjain.notes.model.Note

class NoteRepository(private val database: NoteDatabase) {
    suspend fun insertNote(note:Note) = database.getNoteDao().insertNote(note)
    suspend fun deleteNote(note:Note) = database.getNoteDao().deleteNote(note)
    suspend fun updateNote(note:Note) = database.getNoteDao().updateNote(note)
    fun getAllNotes() = database.getNoteDao().getAllNotes()
    fun searchNote(query:String) = database.getNoteDao().searchNote(query)

}