package com.example.mynotes.ui.fragments.addnote

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentAddNoteBinding
import com.example.mynotes.model.Note
import com.example.mynotes.viewmodel.NotesViewModel
import com.google.android.material.snackbar.Snackbar


class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var viewmodel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        viewmodel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.addNoteBtn.setOnClickListener {
            addNote()
        }

        return binding.root
    }

    private fun addNote() {
        if (TextUtils.isEmpty(binding.titleTxt.text.toString()) || TextUtils.isEmpty(binding.descTxt.text.toString())) {
            Toast.makeText(requireContext(), "Fill In All Details", Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(0, binding.titleTxt.text.toString(), binding.descTxt.text.toString())
            viewmodel.insertNote(note)
            // navigate back to notes fragment
            findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment())

            view?.let {
                Snackbar.make(it, "Note added successfully", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

}