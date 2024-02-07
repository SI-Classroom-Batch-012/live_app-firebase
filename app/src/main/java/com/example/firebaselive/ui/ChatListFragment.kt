package com.example.firebaselive.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.firebaselive.DateUtils
import com.example.firebaselive.MainViewmodel
import com.example.firebaselive.R
import com.example.firebaselive.databinding.FragmentChatListBinding
import com.example.firebaselive.databinding.FragmentNotesBinding
import com.example.firebaselive.model.Chat
import com.example.firebaselive.model.Note
import com.example.firebaselive.ui.adapter.ChatAdapter

class ChatListFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentChatListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region testcode

        //Später noch über UI ausgewählt
        val userId = "kj7nyq3P9PVCYrEKIblYjjCVE5y1"
        //viewmodel.createChat(userId)

//        viewmodel.addMessageToChat("Hallo Firebase", "5EsAeuMmAJcchCghq6Mq")

        //endregion


        viewmodel.chatsRef.addSnapshotListener { value, error ->
            Log.d("SnapshotListener1", "$error")
            if(error == null) {

                Log.d("SnapshotListener2", "$error")

//                val chatList = value?.toObjects(Chat::class.java)!!
                val chatList : List<Pair<String, Chat>> = value!!.documents.map {
                    Pair(
                        it.id,
                        it.toObject(Chat::class.java)!!
                    )
                }
                Log.d("SnapshotListener3", "$chatList , $error")

                val adapter = ChatAdapter(chatList)
                binding.chatsRV.adapter = adapter
            }
        }
    }

}