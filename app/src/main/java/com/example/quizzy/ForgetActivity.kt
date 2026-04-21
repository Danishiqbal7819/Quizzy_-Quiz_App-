package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgetActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var  resetbtn:Button
    lateinit var  register:Button
    private  lateinit var email1: EditText
    private lateinit var checkEmail:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth= FirebaseAuth.getInstance()
        resetbtn=findViewById(R.id.Reset_Now)
        email1=findViewById(R.id.etEmailAddress)
        register=findViewById(R.id.register1)
        checkEmail= findViewById<TextView>(R.id.checkEmail)
        checkEmail.visibility= View.INVISIBLE
        resetbtn.setOnClickListener {
            forgetEmail(email1.text.trim().toString())
        }
        register.setOnClickListener {
            val intent=Intent(this@ForgetActivity,SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun forgetEmail(email: String)  {
        if (email.isBlank()){
            Toast.makeText(this,"Email is missing", Toast.LENGTH_SHORT).show()

        }
        else{
            val auth= Firebase.auth
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Password Reset Email Sent", Toast.LENGTH_LONG).show()
                    checkEmail.visibility= View.VISIBLE
                }
                else{
                    Toast.makeText(this,"Error sending password reset email", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}