package com.example.mynotes.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.adapter.NotesAdapter
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.viewmodel.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.notes_lyt.view.*

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
            if (it.isEmpty()) {
                binding.imageLlyt.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                adapter.setNotes(it)
            }
        })

        return binding.root
    }

    // navigate to addnotes fragment
    private fun goToAddNoteFragment() {
        findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.mode -> {
//                val mode =
//                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
//                        AppCompatDelegate.MODE_NIGHT_YES
//                    } else {
//                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
//                    }
//
//                // Change UI Mode
//                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }

            R.id.delete -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // delete all notes
    private fun deleteAllNotes() {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete notes")
            .setMessage("Are you sure you want to delete all your notes?")

            .setNegativeButton("Cancel") { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton("Delete") { _, _ ->
                // Respond to positive button press
                viewModel.deleteAllNotes()
                binding.recyclerView.visibility = View.GONE
            }
            .show()
    }

}