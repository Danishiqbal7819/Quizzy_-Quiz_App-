package com.example.quizzy

import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivty : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var email: String
    lateinit var password:String
    private  lateinit var email1:EditText
    private  lateinit var password1:EditText
    private  lateinit var forgetPassword:TextView
    private  lateinit var login1:Button
    private  lateinit var register:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@LoginActivty, R.color.SkyBlue)
        setContentView(R.layout.activity_login_activty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth= FirebaseAuth.getInstance()

         email1=findViewById<EditText>(R.id.etEmailAddress)
         password1=findViewById<EditText>(R.id.password1)
         forgetPassword=findViewById<TextView>(R.id.forgot)
         login1=findViewById<Button>(R.id.login1)
        register=findViewById<Button>(R.id.register1)


        forgetPassword.setOnClickListener{
            val intent=Intent(this@LoginActivty,ForgetActivity::class.java)
            startActivity(intent)
        }


        register.setOnClickListener {
            val intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        login1.setOnClickListener {
            email=email1.text.trim().toString()
            password=password1.text.trim().toString()
            login(email,password)

        }
    }



    private fun login(email:String,password:String){
        if (email.isBlank()||password.isBlank()){
            Toast.makeText(this,"Email or password missing",Toast.LENGTH_SHORT).show()
            return
        }
       firebaseAuth.signInWithEmailAndPassword(email,password)
           .addOnCompleteListener(this){
               if (it.isSuccessful){
                   Toast.makeText(this,"Login Succesfull",Toast.LENGTH_SHORT).show()
                   val intent=Intent(this,welcomeActivity::class.java)
                   startActivity(intent)
                   finish()
               }
               else{
                   Toast.makeText(this,"Incorrect Email or password",Toast.LENGTH_SHORT).show()
               }
           }
    }
}