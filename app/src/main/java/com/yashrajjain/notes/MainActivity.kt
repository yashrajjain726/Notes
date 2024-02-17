package com.yashrajjain.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yashrajjain.notes.db.NoteDatabase
import com.yashrajjain.notes.repository.NoteRepository
import com.yashrajjain.notes.viewmodel.NoteViewModel
import com.yashrajjain.notes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
    }
    fun setUpViewModel(){
        val repository = NoteRepository(NoteDatabase(this))
        val factory = NoteViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
    }
}