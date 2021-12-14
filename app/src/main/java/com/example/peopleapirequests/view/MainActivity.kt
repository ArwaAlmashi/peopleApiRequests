package com.example.peopleapirequests.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.Person
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.peopleapirequests.R
import com.example.peopleapirequests.databinding.ActivityMainBinding
import com.example.peopleapirequests.databinding.AlertMessageBinding
import com.example.peopleapirequests.model.PersonModel
import com.example.peopleapirequests.model.PersonModelItem
import com.example.peopleapirequests.recyclerview.RecyclerviewAdapter
import com.example.peopleapirequests.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerviewAdapter
    private lateinit var people: PersonModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddPersonActivity::class.java)
            startActivity(intent)
        }
    }

    // Set UI
    private fun setUI() {
        supportActionBar!!.hide()
        recyclerView = binding.recyclerview
        people = PersonModel()
        adapter = RecyclerviewAdapter(this, people)
        recyclerView.adapter = adapter
        getPeopleData()
    }

    // Alert
    fun customAlert(context: Context, person: PersonModelItem) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.alert_message)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val nameInput = dialog.findViewById<EditText>(R.id.update_name_et)
        val locationInput = dialog.findViewById<EditText>(R.id.update_location_et)
        val updateButton = dialog.findViewById<Button>(R.id.update_button)
        val deleteButton = dialog.findViewById<Button>(R.id.delete_button)

        nameInput.setText(person.name)
        locationInput.setText(person.location)
        dialog.show()


        updateButton.setOnClickListener {
            person.name = nameInput.text.toString()
            person.location = locationInput.text.toString()
            updatePersonData(person)
            dialog.dismiss()
        }

        deleteButton.setOnClickListener {
            deletePersonData(person)
            dialog.dismiss()
        }

    }



    // GET
    private fun getPeopleData() {
        val personModel = ApiClient().retrofitBuilder().getApiData()
        personModel.enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                people = response.body()!!
                Log.d("MainActivity", "${response.body()}")
                adapter.update(people)
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                Log.d("MainActivity", "GET error: ${t.message}")
            }
        })
    }

    // UPDATE
    private fun updatePersonData(person: PersonModelItem) {
        ApiClient().retrofitBuilder().updateApiData(
            person.pk,
            person
        ).enqueue(object : Callback<PersonModelItem> {
            override fun onResponse(
                call: Call<PersonModelItem>,
                response: Response<PersonModelItem>
            ) {
                println("Success update")
//                for (changePerson in people) {
//                    if (person.pk == changePerson.pk) {
//                        changePerson.name = person.name
//                        changePerson.location = person.location
//                    }
//                }
                getPeopleData()

            }

            override fun onFailure(call: Call<PersonModelItem>, t: Throwable) {
                println("Failure Update: ${t.message}")
            }

        })

    }

    // DELETE
    private fun deletePersonData(person: PersonModelItem) {
        ApiClient().retrofitBuilder().deleteApiData(person.pk).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                println("Success Delete ")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Failure Delete: ${t.message}")
            }

        })

    }


}