package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mandatoryassignment.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // ...
// Initialize Firebase Auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        if (currentUser != null) {
        binding.viewError.setText(currentUser.toString() + " already logged in")
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("ORANGE", " signInWithEmail:success")
                        val user = auth.currentUser
                        binding.viewError.setText("Succesfully logged in as: " + user.toString())
                    } else {
                        Log.d("ORANGE", " signInWithEmail:failure", task.exception)
                        binding.viewError.setText("Authentication failed")
                    }
                }
        }
    }
}