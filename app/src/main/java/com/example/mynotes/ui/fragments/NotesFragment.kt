package com.example.mynotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.adapter.NotesAdapter
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.viewmodel.NotesViewModel

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        setHasOptionsMenu(true)

        val adapter = NotesAdapter()

        binding.apply {

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)

            floatingActionButton.setOnClickListener {
                goToAddNoteFragment()
            }
        }

        viewModel.getAllNotes.observe(viewLifecycleOwner, {
            adapter.setNotes(it)
        })

        return binding.root
    }

    // navigate to addnotes fragment
    private fun goToAddNoteFragment() {
        findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.delete_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    // delete all notes
    private fun deleteAllNotes() {
        viewModel.deleteAllNotes()
    }

}