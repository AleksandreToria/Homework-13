package com.example.homework13

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.CardLayoutBinding
import java.util.Calendar

class Adapter :
    ListAdapter<List<FormField>, RecyclerView.ViewHolder>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<List<FormField>>() {
        override fun areItemsTheSame(oldItem: List<FormField>, newItem: List<FormField>): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: List<FormField>,
            newItem: List<FormField>
        ): Boolean {
            return oldItem == newItem
        }

    }

    private var formDataList: MutableList<FormField> = mutableListOf()


    fun setCurrentUserData(userDataList: List<FormField>) {
        formDataList.clear()
        formDataList.addAll(userDataList)
    }

    inner class ViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userDataList: List<FormField>) {
            binding.linearLayout.removeAllViews()
            val context = binding.root.context
            val parentLayout = binding.linearLayout

            userDataList.forEachIndexed { _, userDataField ->
                val editText = AppCompatEditText(context)
                editText.hint = userDataField.hint

                editText.setText(userDataField.enteredValue)

                editText.inputType = when (userDataField.hint) {
                    "Email" -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    "phone" -> InputType.TYPE_CLASS_PHONE
                    "FullName" -> InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                    "Birthday" -> InputType.TYPE_NULL
                    "Gender" -> InputType.TYPE_NULL
                    else -> InputType.TYPE_CLASS_TEXT
                }

                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (editText.hint == "Birthday") {
                            showDatePicker(context, editText, userDataField)
                        }

                        if (editText.hint == "Gender") {
                            showGenderPicker(context, editText, userDataField)
                        }
                    }
                }

                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        userDataField.enteredValue = s.toString()
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                parentLayout.addView(editText)
            }
        }

    }

    fun getCurrentUserData(): List<FormField> {
        return formDataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = getItem(position)
        when (holder) {
            is ViewHolder -> holder.bind(model)
        }
    }

    private fun showDatePicker(context: Context, editText: EditText, field: FormField) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                editText.setText("$selectedYear-${selectedMonth + 1}-$selectedDay")
                field.enteredValue = editText.text.toString()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showGenderPicker(context: Context, editText: EditText, field: FormField) {
        val genders = arrayOf("Male", "Female", "Other")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Gender")
        builder.setItems(genders) { _, which ->
            editText.setText(genders[which])
            field.enteredValue = editText.text.toString()
        }

        val dialog = builder.create()
        dialog.show()
    }
}






