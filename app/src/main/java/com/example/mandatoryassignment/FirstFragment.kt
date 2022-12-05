package com.example.mandatoryassignment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandatoryassignment.databinding.FragmentFirstBinding
import com.example.mandatoryassignment.Models.CatsViewModel
import com.example.mandatoryassignment.Models.MyAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener { view ->
            val CurrentUser = auth.currentUser
            if (CurrentUser != null) {
                val action = FirstFragmentDirections.actionFirstFragmentToCatCreationFragment()
                findNavController().navigate(action)
            } else {
                binding.viewError.setText("Only logged in users can create cats")
            }
        }

            catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
                binding.progressbar.visibility = View.GONE
                binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE
                if (cats != null) {
                    val adapter = MyAdapter(cats) { position ->
                        val action =
                            FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                        findNavController().navigate(action)
                    }
                    var columns = 2
                    val currentOrientation = this.resources.configuration.orientation
                    if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        columns = 4
                    }
                    binding.recyclerView.layoutManager =
                        GridLayoutManager(requireActivity(), columns)
                    binding.recyclerView.adapter = adapter
                }
            }
            catsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
                binding.textviewMessage.text = errorMessage
            }
            catsViewModel.reload()

            binding.swiperefresh.setOnClickListener {
                catsViewModel.reload()
                binding.swiperefresh.isRefreshing = false
            }

/*            catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cats)
                binding.spinnerCats.adapter = adapter
                binding.spinnerCats.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val action =
                            FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                        findNavController().navigate(action)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }*/
        binding.buttonShowDetails.setOnClickListener {
            val position = binding.spinnerCats.selectedItemPosition
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
            findNavController().navigate(action)
        }

        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> catsViewModel.sortByName()
                1 -> catsViewModel.sortByNameDescending()
                2 -> catsViewModel.sortByDate()
                3 -> catsViewModel.sortByDateDescending()
            }
        }

        binding.buttonFilter.setOnClickListener {
            val name = binding.edittextFilterTitle.text.toString().trim()
            if (name.isBlank()) {
                binding.edittextFilterTitle.error = "No name"
                return@setOnClickListener
            }
            catsViewModel.filterByName(name)
        }

        }
    }

