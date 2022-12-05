package com.example.mandatoryassignment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatoryassignment.Models.CatsViewModel
import com.example.mandatoryassignment.databinding.FragmentSecondBinding
import com.example.mandatoryassignment.Models.Cat
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val catsViewModel: CatsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val cat = catsViewModel[position]
        if (cat == null) {
            binding.textviewMessage.text = "No such cat!"
            return
        }

        binding.editTextName.setText(cat.name)
        binding.editTextDate.setText(cat.date.toString())
        binding.editTextDescription.setText(cat.description)
        binding.editTextPlace.setText(cat.place)
        binding.editTextReward.setText(cat.reward + " " + "kr.")

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonDelete.setOnClickListener {
            val CurrentUser = auth.currentUser

            if (CurrentUser != null && CurrentUser.toString() == cat.userId) {
                catsViewModel.delete(cat.id)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}