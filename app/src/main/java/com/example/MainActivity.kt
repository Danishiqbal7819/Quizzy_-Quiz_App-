package com.example

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.quizzy.AddNotesActivity
import com.example.quizzy.LoginActivty
import com.example.quizzy.R
import com.example.quizzy.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.SkyBlue)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            val intent=Intent(this@MainActivity,AddNotesActivity::class.java)
            startActivity(intent)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, drawerLayout)

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Logout -> {
                    alertMessage()
                    true
                }
                R.id.settings -> {
                    alertMaessageSetting()
                    true
                }
                R.id.nav_home -> {
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_gallery -> {
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.navigate(R.id.nav_gallery)
                    true
                }
                R.id.nav_slideshow -> {
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.navigate(R.id.nav_slideshow)
                    true
                }
                else -> false
            }
        }

    }

    private fun alertMaessageSetting() {
        val builder :AlertDialog.Builder=AlertDialog.Builder(this)
        builder.setTitle("Setting")
        builder.setMessage("Sorry for inconvenience!!\nSettings not available.")
        builder.setCancelable(false)
//        builder.setView(R.layout.itemsrecyclerview)
        builder.setIcon(R.drawable.setting)
        builder.setPositiveButton("Ok") {it, _ ->
            it.cancel()
        }
        builder.setNegativeButton("Cancel"){ it, _ ->
            it.cancel()
        }
        builder.show()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun alertMessage(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        firebaseAuth= FirebaseAuth.getInstance()
        builder.setMessage("Are You Sure?")
        builder.setTitle("Logging out")
        builder.setCancelable(false)
        builder.setIcon(R.drawable.baseline_person_24)
        builder.setPositiveButton("Yes") { _, _ ->
            firebaseAuth.signOut()
            Toast.makeText(this,"Log_out Succesfully",Toast.LENGTH_SHORT).show()
            val intent=Intent(this@MainActivity,LoginActivty::class.java)
            finish()
            startActivity(intent)}
        builder.setNegativeButton("Cancel"){it,i -> it.cancel() }
        builder.show()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}