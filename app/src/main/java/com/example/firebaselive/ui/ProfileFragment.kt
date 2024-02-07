package com.example.firebaselive.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.firebaselive.MainViewmodel
import com.example.firebaselive.model.Profile
import com.example.firebaselive.R
import com.example.firebaselive.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                //uri enthält Verweis auf unser Bild, damit können wir weiterarbeiten
                viewmodel.uploadProfilePicture(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBTN.setOnClickListener {
            viewmodel.logout()
        }

        viewmodel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                //binding.userTV.text = it.uid
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewmodel.profileRef.addSnapshotListener { snapshot, error ->

            val profile = snapshot?.toObject(Profile::class.java)!!
            binding.userTV.text = profile.isPremium.toString()

            if (profile.profilePicture != "") {
                binding.profileIV.load(profile.profilePicture)
            }
        }

        binding.profileIV.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.noteBTN.setOnClickListener {
            findNavController().navigate(R.id.notesFragment)
        }

        binding.chatBTN.setOnClickListener {
            findNavController().navigate(R.id.chatListFragment)
        }

    }

}