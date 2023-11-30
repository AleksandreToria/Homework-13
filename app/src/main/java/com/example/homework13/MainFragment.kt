package com.example.homework13

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var adapter: Adapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.regBtn.setOnClickListener {
            // Get the current user data from the adapter
            val userDataList = adapter.getCurrentUserData()

            // Check for required fields
            if (validateUserData(userDataList)) {
                // If all required fields are filled, navigate to the next fragment
                val action = MainFragmentDirections.actionMainFragmentToInfoFragment(userDataList.toTypedArray())
                findNavController().navigate(action)
            } else {
                // Display an error message or take appropriate action
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        mainViewModel.formData.observe(viewLifecycleOwner) { formDataList ->
            adapter.submitList(formDataList)

            adapter.setCurrentUserData(formDataList.flatten())
        }

        mainViewModel.loadJsonData(requireContext(), "Information.json")
    }

    private fun validateUserData(userDataList: List<FormField>): Boolean {
        for (userDataField in userDataList) {
            if (userDataField.required && userDataField.enteredValue.isNullOrEmpty()) {
                return false // Required field is empty
            }
        }
        return true // All required fields are filled
    }
}









