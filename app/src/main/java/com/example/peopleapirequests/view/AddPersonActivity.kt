package com.example.peopleapirequests.view

import android.app.Person
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.peopleapirequests.databinding.ActivityAddPersonBinding
import com.example.peopleapirequests.model.PersonHolder
import com.example.peopleapirequests.model.PersonModelItem
import com.example.peopleapirequests.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()

        binding.apply {
            addNewPersonButton.setOnClickListener {
                val name = nameEt.text
                val location = locationEt.text
                if (name != null || location != null) {
                    postPersonData(name.toString(), location.toString())
                } else {
                    Toast.makeText(
                        this@AddPersonActivity,
                        "Please fill all text fields",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    // POST
    private fun postPersonData(name: String, location: String) {
        ApiClient().retrofitBuilder()
            .postApiData(PersonHolder(name, location))
            .enqueue(object : Callback<PersonHolder> {
                override fun onResponse(
                    call: Call<PersonHolder>,
                    response: Response<PersonHolder>
                ) {
                    val intent =
                        Intent(this@AddPersonActivity, MainActivity::class.java)
                    startActivity(intent)

                }
                override fun onFailure(call: Call<PersonHolder>, t: Throwable) {
                    println("Failure POST: ${t.message}")
                }

            })
    }

    private fun setUI() {
        supportActionBar?.hide()
    }
}
