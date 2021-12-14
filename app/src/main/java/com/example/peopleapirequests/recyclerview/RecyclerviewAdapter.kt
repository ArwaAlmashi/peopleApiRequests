package com.example.peopleapirequests.recyclerview

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.peopleapirequests.R
import com.example.peopleapirequests.databinding.RowBinding
import com.example.peopleapirequests.model.PersonModel
import com.example.peopleapirequests.view.MainActivity

class RecyclerviewAdapter(private val context: Context, private var people: PersonModel) :
    RecyclerView.Adapter<RecyclerviewAdapter.personViewHolder>() {

    class personViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerviewAdapter.personViewHolder {
        return personViewHolder(
            RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter.personViewHolder, position: Int) {
        val person = people[position]
        holder.binding.apply {
            idTv.text = "${person.pk}"
            nameTv.text = person.name
            locationTv.text = person.location
        }
        holder.binding.row.setOnClickListener {
            MainActivity().customAlert(context, person)
        }
    }

    override fun getItemCount() = people.size

    fun update(newList: PersonModel) {
        people = newList
        notifyDataSetChanged()
    }
}