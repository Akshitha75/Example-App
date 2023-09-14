package com.login.exampleapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val phone = findViewById<Button>(R.id.phone)


        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if(email == null && password==null ) {
                Toast.makeText(this, "Please Enter username and password", Toast.LENGTH_SHORT).show()

            }else {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.putExtra("Username", email)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "Sorry,you are not Registered", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
            }
        }
        phone.setOnClickListener {

            val intent = Intent(this,OtpLogin::class.java)
            startActivity(intent)

        }


    }
}