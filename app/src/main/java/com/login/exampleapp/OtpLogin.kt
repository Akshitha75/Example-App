package com.login.exampleapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpLogin : AppCompatActivity() {
    private lateinit var phoneNumberEditText: EditText
    private lateinit var otpEditText: EditText
    private lateinit var sendOtpButton: Button
    private lateinit var verifyOtpButton: Button
    private lateinit var resendOtpButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_login)


            phoneNumberEditText = findViewById(R.id.phonenumber)
            otpEditText = findViewById(R.id.otpEditText)
            sendOtpButton = findViewById(R.id.sendotp)
            verifyOtpButton = findViewById(R.id.verifyotp)
            resendOtpButton = findViewById(R.id.resendotp)

            auth = FirebaseAuth.getInstance()

            sendOtpButton.setOnClickListener {
                val phoneNumber = phoneNumberEditText.text.toString()
                sendOtp(phoneNumber)
            }

            verifyOtpButton.setOnClickListener {
                val otp = otpEditText.text.toString()
                verifyOtp(otp)
            }

            resendOtpButton.setOnClickListener {
                val phoneNumber = phoneNumberEditText.text.toString()
                resendOtp(phoneNumber)
            }

            initializeOtpCallbacks()
        }

        private fun sendOtp(phoneNumber: String) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
            )
        }

        private fun verifyOtp(otp: String) {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
            signInWithPhoneAuthCredential(credential)
        }

        private fun resendOtp(phoneNumber: String) {
            sendOtp(phoneNumber)
            // Handle the resend action (e.g., show a confirmation message)
        }

        private fun initializeOtpCallbacks() { callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Automatic verification completed; you can handle it here if needed
            }


                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@OtpLogin,
                        "Verification failed: ${e.message}",
                        Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@OtpLogin.verificationId = verificationId
                    // Code sent, enable UI elements for OTP entry
                    otpEditText.visibility = View.VISIBLE
                    verifyOtpButton.visibility = View.VISIBLE
                    resendOtpButton.visibility = View.VISIBLE
                }
            }
        }

        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Authentication successful
                        val user = auth.currentUser
                        val intent = Intent(this, DisplayActivity::class.java)
                        startActivity(intent)

                    } else {
                        // Authentication failed
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

        }
    }
