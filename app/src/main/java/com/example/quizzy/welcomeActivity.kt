package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.MainActivity
import com.google.firebase.auth.FirebaseAuth

class welcomeActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth= FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser!=null){
            redirect("Main")
        }

        findViewById<Button>(R.id.GetStarted).setOnClickListener {
            redirect("Login")
        }
    }

    private fun redirect(value:String){
        val intent:Intent=when(value){
            "Login"->Intent(this,LoginActivty::class.java)
            "Main"->Intent(this,MainActivity::class.java)
            else->throw Exception("no path Exist")
        }
        startActivity(intent)
        finish()

    }
}