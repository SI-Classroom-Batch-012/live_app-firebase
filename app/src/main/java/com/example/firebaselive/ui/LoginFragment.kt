package com.example.firebaselive.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firebaselive.MainViewmodel
import com.example.firebaselive.R
import com.example.firebaselive.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBTN.setOnClickListener {
            viewmodel.login(
                binding.emailET.text.toString(),
                binding.passwordET.text.toString()
            )
        }

        binding.registerBTN.setOnClickListener {
            viewmodel.register(
                binding.emailET.text.toString(),
                binding.passwordET.text.toString()
            )
        }


        viewmodel.user.observe(viewLifecycleOwner){
            if(it != null){
                //User ist eingeloggt
                findNavController().navigate(R.id.profileFragment)
            }
        }



    }

}