package com.example.mynotes.ui.fragments

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
import com.example.mynotes.databinding.FragmentUpdateBinding
import com.example.mynotes.model.Note
import com.example.mynotes.viewmodel.NotesViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewmodel: NotesViewModel
    private lateinit var args: UpdateFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)

        args = UpdateFragmentArgs.fromBundle(requireArguments())

        binding.updateTitleTxt.setText(args.currentNote.title)
        binding.updateDescTxt.setText(args.currentNote.description)

        viewmodel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.updateNoteBtn.setOnClickListener {
            updateNote()
        }

        return binding.root
    }

    private fun updateNote() {

        if (TextUtils.isEmpty(binding.updateTitleTxt.text.toString()) || TextUtils.isEmpty(binding.updateDescTxt.text.toString())) {
            Toast.makeText(requireContext(), "Fill In All Details", Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(
                args.currentNote.id,
                binding.updateTitleTxt.text.toString(),
                binding.updateDescTxt.text.toString()
            )
            viewmodel.updateNote(note)

            // navigate back to notes fragment
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToNotesFragment())

            view?.let {
                Snackbar.make(it, "Note updated successfully", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

}