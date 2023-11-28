package com.example.homework13

import android.app.DatePickerDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.FieldLayoutBinding
import java.util.Calendar

class Adapter :
    ListAdapter<FormField, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<FormField>() {
        override fun areItemsTheSame(oldItem: FormField, newItem: FormField): Boolean {
            return oldItem.field_id == newItem.field_id
        }

        override fun areContentsTheSame(oldItem: FormField, newItem: FormField): Boolean {
            return oldItem == newItem
        }
    }) {

    private var userData: List<FormField> = emptyList()

    fun setData(fields: List<FormField>) {
        submitList(fields)
        userData = fields
    }

    fun getCurrentUserData(): List<FormField> {
        return userData
    }

    inner class ViewHolder(private val binding: FieldLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: FormField) {
            with(binding.et) {
                hint = field.hint

                inputType = when (field.hint) {
                    "Email" -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    "phone" -> InputType.TYPE_CLASS_PHONE
                    "FullName" -> InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                    else -> InputType.TYPE_CLASS_TEXT
                }

                setText(field.enteredValue)

                setOnClickListener {
                    if (field.hint == "Birthday" || field.hint == "Gender") {
                        inputType = InputType.TYPE_NULL
                        isFocusable = false
                    }

                    if (field.hint == "Birthday") {
                        showDatePicker(context, this, field)
                    }

                    if (field.hint == "Gender") {
                        showGenderPicker(context, this, field)
                    }
                }

                addTextChangedListener { editable ->
                    field.enteredValue = editable.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            FieldLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(getItem(position))
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


