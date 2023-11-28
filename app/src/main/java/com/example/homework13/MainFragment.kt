package com.example.homework13

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework13.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        adapter = Adapter()

        val jsonParser = JsonParser(requireContext())
        val formData = jsonParser.parseFormData("Information.json")

        val firstList = formData?.flatten() ?: emptyList()
        adapter.setData(firstList)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.regBtn.setOnClickListener {
            val userDataList = adapter.getCurrentUserData()

            val action =
                MainFragmentDirections.actionMainFragmentToInfoFragment(userDataList.toTypedArray())

            findNavController().navigate(action)
        }
    }
}



