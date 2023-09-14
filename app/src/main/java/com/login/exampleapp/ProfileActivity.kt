package com.login.exampleapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val location = findViewById<CardView>(R.id.location)
        val name = findViewById<TextView>(R.id.name)
        val email= findViewById<TextView>(R.id.email)
        val dob = findViewById<TextView>(R.id.dob)
        val image = findViewById<ImageView>(R.id.imageView3)
        val occupation = findViewById<TextView>(R.id.occupation)
        location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        var strUser: String = intent.getStringExtra("Username").toString()

        if(strUser.equals("exampleapp@gmail.com")){
            name.setText("John")
            email.setText(strUser)
            dob.setText("15/7/1993")
            occupation.setText("Painter")

        }else if(strUser.equals("kim121@gmail.com")){
            name.setText("kim")
            email.setText(strUser)
            dob.setText("10/5/2007")
            occupation.setText("Dancer")

        }

        else{
            Toast.makeText(this, "user not found", Toast.LENGTH_SHORT)
                .show()
        }
    }
}