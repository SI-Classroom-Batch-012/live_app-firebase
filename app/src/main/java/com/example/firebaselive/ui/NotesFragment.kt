package com.example.firebaselive.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firebaselive.DateUtils
import com.example.firebaselive.MainViewmodel
import com.example.firebaselive.R
import com.example.firebaselive.databinding.FragmentLoginBinding
import com.example.firebaselive.databinding.FragmentNotesBinding
import com.example.firebaselive.model.Note


class NotesFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBTN.setOnClickListener {
            viewmodel.saveNote(
                binding.titleET.text.toString(),
                binding.contentET.text.toString()
            )

            binding.titleET.setText("")
            binding.contentET.setText("")

        }

        viewmodel.notesRef.addSnapshotListener { snapshot, error ->

            snapshot?.let {
                val notes: List<Note> = snapshot.toObjects(Note::class.java)
                Log.d(
                    "NoteList", "${
                        notes.map { note ->
                            "{${note.title} - ${note.content} -" +
                                    " ${DateUtils.toSimpleString(note.timestamp.toDate())}}"
                        }
                    }"
                )

            }


        }

    }

}