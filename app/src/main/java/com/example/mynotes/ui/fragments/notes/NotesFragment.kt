package com.example.mynotes.ui.fragments.notes

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var noteadapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        setHasOptionsMenu(true)

        swipeToDeleteNote()
        noteadapter = NotesAdapter()

        binding.apply {

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = noteadapter
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
                noteadapter.differ.submitList(it)
            }
        })

        return binding.root
    }

    private fun swipeToDeleteNote() {

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get current item
                val position = viewHolder.adapterPosition
                val note = noteadapter.differ.currentList[position]
                viewModel.deleteNote(note)

                Snackbar.make(
                    binding.root,
                    getString(R.string.note_deleted),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction("Undo") {
                            viewModel.insertNote(note)
                            image_llyt.visibility = View.GONE
                        }
                        show()
                    }
            }
        }

        // attach swipe to recyclerview
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
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
            R.id.delete -> {
                if (noteadapter.differ.currentList.size == 0) {
                    Toast.makeText(context, "Currently there are no notes!!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    deleteAllNotes()
                }

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