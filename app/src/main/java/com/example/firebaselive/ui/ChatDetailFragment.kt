package com.example.firebaselive.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.firebaselive.MainViewmodel
import com.example.firebaselive.R
import com.example.firebaselive.databinding.FragmentChatDetailBinding
import com.example.firebaselive.databinding.FragmentChatListBinding
import com.example.firebaselive.model.Message
import com.example.firebaselive.ui.adapter.MessageAdapter

class ChatDetailFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentChatDetailBinding
    private val args: ChatDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatId = args.chatId

        viewmodel.getMessageRef(chatId).addSnapshotListener { value, error ->
            if(error == null){
                val messageList : List<Message> = value!!.toObjects(Message::class.java)

                val adapter = MessageAdapter(messageList, viewmodel.auth.currentUser!!.uid)
                binding.messagesRV.adapter = adapter
            }
        }

        binding.sendBTN.setOnClickListener {
            val message = binding.messageET.text.toString()
            binding.messageET.setText("")
            viewmodel.addMessageToChat(message, chatId)
        }

    }

}