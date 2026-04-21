package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var email:String
    lateinit var password:String
    lateinit var name1:String
    lateinit var phone1:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@SignupActivity, R.color.SkyBlue)
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth=FirebaseAuth.getInstance()

        val etEmailAddress=findViewById<EditText>(R.id.etemail)
        val etPaaword=findViewById<EditText>(R.id.etpassword)
        val name=findViewById<EditText>(R.id.name)
        val phone=findViewById<EditText>(R.id.phone)
        val SignupBtn=findViewById<Button>(R.id.Signup)
        val LoginNow=findViewById<Button>(R.id.loginNow)

 LoginNow.setOnClickListener {
     val intent=Intent(this,LoginActivty::class.java)
     startActivity(intent)
     finish()
 }

        SignupBtn.setOnClickListener {
            email=etEmailAddress.text.trim().toString()
            password=etPaaword.text.trim().toString()
            name1=name.text.trim().toString()
            phone1=phone.text.trim().toString()

            if (email.isBlank()||password.isBlank()||name1.isBlank()||phone1.isBlank()){
                Toast.makeText(this,"plz enter all data",Toast.LENGTH_SHORT).show()
            }
            else
            {
                SignupUser(email,password)
            }
        }
    }
    private fun SignupUser(email:String,password:String){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Succesfully SignUp",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,welcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Error while Signing Up",Toast.LENGTH_SHORT).show()
                }
            }
    }
}