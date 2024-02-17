package com.yashrajjain.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashrajjain.notes.model.Note
import com.yashrajjain.notes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private  val repository: NoteRepository): AndroidViewModel(app) {
    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }
    fun deleteNote(note:Note) = viewModelScope.launch{
        repository.deleteNote(note)
    }
    fun updateNote(note:Note) = viewModelScope.launch {
        repository.updateNote(note)
    }
    fun searchNote(query:String) = repository.searchNote(query)
    fun getAllNotes() = repository.getAllNotes()

}