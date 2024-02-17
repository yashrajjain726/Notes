package com.yashrajjain.notes.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.yashrajjain.notes.MainActivity
import com.yashrajjain.notes.R
import com.yashrajjain.notes.databinding.FragmentEditNoteBinding
import com.yashrajjain.notes.model.Note
import com.yashrajjain.notes.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var dataBinding: FragmentEditNoteBinding?=null
    private val binding  get() = dataBinding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val args: EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        viewModel = (activity as MainActivity).viewModel
        currentNote = args.note!!
        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)
        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()
            if(noteTitle.isNotEmpty()){
                val note = Note(currentNote.id, noteTitle, noteDesc)
                viewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
                Toast.makeText(view.context, "Note updated successfully !", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(view.context, "Note title is required, Please enter Note Title !", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you wan to delete this note, Are you sure?")
            setPositiveButton("Delete"){_,_ ->
                viewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note deleted successfully !", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding = null
    }
}