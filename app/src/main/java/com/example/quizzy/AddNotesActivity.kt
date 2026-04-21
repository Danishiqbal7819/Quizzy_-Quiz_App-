package com.example.quizzy

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.AdapterNotes
import com.example.todo.NoteDao
import com.example.todo.NoteDatabase
import com.example.todo.NoteEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class AddNotesActivity : AppCompatActivity() {
    private lateinit var noteDao: NoteDao
    private lateinit var datetext: TextView
    private lateinit var editText1: EditText
    private lateinit var recyclerID:RecyclerView
    private lateinit var yourtext: String
    private lateinit var date: String
    lateinit var adapter: AdapterNotes
    private lateinit var  notesList:List<NoteEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@AddNotesActivity, R.color.SkyBlue)
        setContentView(R.layout.activity_add_notes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        datetext = findViewById(R.id.datetext)
        editText1=findViewById(R.id.edittext1)
        recyclerID=findViewById<RecyclerView>(R.id.recyclerView2)
       val addbtn= findViewById<Button>(R.id.add)
        date=datetext.text.trim().toString()
        yourtext=editText1.text.trim().toString()

        DisplayNotes()

        findViewById<TextView>(R.id.datetext).setOnClickListener {
            showDatePickerDialog(datetext)
        }

        addbtn.setOnClickListener {
            date=datetext.text.trim().toString()
            yourtext=editText1.text.trim().toString()
            if (yourtext.isEmpty()){
                Toast.makeText(this,"Empty note,plz write something",Toast.LENGTH_SHORT).show()
            }
            else{
                yourNotes(yourtext,date)
            }

        }
        findViewById<Button>(R.id.deleteAll).setOnClickListener {
            deleteAll()
        }
        findViewById<TextView>(R.id.Exit).setOnClickListener {
            finish()
        }

            }

    private fun DisplayNotes() {
        val noteDatabase = NoteDatabase.getInstance(this)
        noteDao = noteDatabase.noteDao()
        lifecycleScope.launch {
            notesList = noteDao.getAllNotes()
            withContext(Dispatchers.Main) {
                adapter = AdapterNotes(applicationContext, notesList)
                recyclerID.layoutManager = LinearLayoutManager(applicationContext)
                recyclerID.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }
    private fun deleteAll() {
        val noteDatabase = NoteDatabase.getInstance(this)
        noteDao = noteDatabase.noteDao()
        val builder :AlertDialog.Builder=AlertDialog.Builder(this@AddNotesActivity)
        builder.setTitle("Deleting All??")
        builder.setMessage("Are you sure to delete all notes")
        builder.setCancelable(false)
        builder.setIcon(R.drawable.ic4)
        builder.setPositiveButton("Yes"){ it, _ ->
            it.cancel()
            lifecycleScope.launch {
                noteDao.emptyTable()
                withContext(Dispatchers.Main) {
                     notesList = noteDao.getAllNotes()
                    adapter = AdapterNotes(applicationContext, notesList )
                    recyclerID.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerID.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
        builder.setNegativeButton("Cancel"){ it, _ ->
            it.cancel()
        }
        builder.show()
       }

    private fun yourNotes(yourtext: String, date: String) {
        val noteDatabase = NoteDatabase.getInstance(this)
        noteDao = noteDatabase.noteDao()
        val note = NoteEntity(0, yourtext, date)
        lifecycleScope.launch {
            noteDao.insertNote(note)
             notesList = noteDao.getAllNotes()
            withContext(Dispatchers.Main) {
                adapter = AdapterNotes(applicationContext, notesList)
                recyclerID.layoutManager = LinearLayoutManager(applicationContext)
                recyclerID.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun showDatePickerDialog(textView:TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            textView.context,
            { _, selectedYear, selectedMonth, selectedDay ->
                textView.text = date
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                textView.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
