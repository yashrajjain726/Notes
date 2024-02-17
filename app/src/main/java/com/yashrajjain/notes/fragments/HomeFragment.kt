package com.yashrajjain.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yashrajjain.notes.MainActivity
import com.yashrajjain.notes.R
import com.yashrajjain.notes.adapter.NoteAdapter
import com.yashrajjain.notes.databinding.FragmentHomeBinding
import com.yashrajjain.notes.model.Note
import com.yashrajjain.notes.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {
    private var dataBinding: FragmentHomeBinding?=null
    private val binding get() = dataBinding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner,Lifecycle.State.RESUMED)
        viewModel = (activity as MainActivity).viewModel
        setupHomeRv()
        binding.addNoteFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }
    private fun setupHomeRv(){
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity?.let{
            viewModel.getAllNotes().observe(viewLifecycleOwner){ note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }
    private fun updateUI(notes:List<Note>?){
        if(notes!=null){
            if(notes.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null){
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)
        val search = menu.findItem(R.id.searchMenu).actionView as SearchView
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
    fun searchNote(query:String){
        val searchQuery = "%$query"
        viewModel.searchNote(searchQuery).observe(this) {
            noteAdapter.differ.submitList(it)
        }
    }
}