package com.example.homework13

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.homework13.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {
    private lateinit var userData: Array<FormField>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userData =
            requireArguments().getParcelableArray("moveData") as? Array<FormField> ?: emptyArray()

        userData.forEach { field ->
            val textView = TextView(requireContext())

            val displayText = "${field.field_id}: ${field.enteredValue}"
            textView.text = displayText
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            textView.gravity = Gravity.CENTER

            binding.container.addView(textView)
        }
    }
}



