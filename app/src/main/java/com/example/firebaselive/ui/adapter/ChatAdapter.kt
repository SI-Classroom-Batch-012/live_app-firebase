package com.example.firebaselive.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselive.databinding.ChatItemBinding
import com.example.firebaselive.model.Chat
import com.example.firebaselive.ui.ChatListFragmentDirections

class ChatAdapter (val dataset: List<Pair<String, Chat>>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = dataset[position]

        val chatId = item.first
        val chat = item.second

        holder.binding.chatTV.text = chatId

        holder.binding.chatCV.setOnClickListener {

            holder.itemView.findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToChatDetailFragment(chatId))

        }

    }
}