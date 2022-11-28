package com.example.mandatoryassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mandatoryassignment.Models.Cat
import com.example.mandatoryassignment.Models.CatsViewModel
import com.example.mandatoryassignment.databinding.FragmentCatCreationBinding
import com.example.mandatoryassignment.databinding.FragmentFirstBinding

class CatCreationFragment : Fragment() {

    private var _binding: FragmentCatCreationBinding? = null
    private val binding get() = _binding!!
    private val catsViewModel: CatsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.CreateCatButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val description = binding.EditDescription.text.toString().trim()
            val place = binding.EditPlace.text.toString().trim()
            val reward = binding.EditReward.text.toString().trim()
            val date = binding.DatePicker.text.toString().trim().toLong()

            val cat = Cat(0, name, description, place, reward, userId = "0", date, null)

            catsViewModel.add(cat)
        }
    }


}