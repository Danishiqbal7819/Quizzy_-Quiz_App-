package com.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modal_class.Quiz
import com.example.quizzy.R
import com.example.quizzy.ShowquestionsActivity
import com.example.todo.NoteDao
import com.example.todo.NoteDatabase
import com.example.todo.NoteEntity
import com.example.utils.colorpicker
import com.example.utils.iconpicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterNotes (val context: Context, var notes:List<NoteEntity>):

        RecyclerView.Adapter<AdapterNotes.notesViewHolder>() {

    private lateinit var noteDao: NoteDao

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
            val view= LayoutInflater.from(context).inflate(R.layout.item_notes,parent,false)
            return notesViewHolder(view)
        }

        override fun getItemCount(): Int {
            return  notes.size
        }
        override fun onBindViewHolder(holder:notesViewHolder, position: Int) {
            holder.textViewdate.text=notes[position].date
            holder.textViewNotes.text=notes[position].text
            holder.deletebtn.setOnClickListener {
                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()

                    GlobalScope.launch {
                        deleteNoteById(notes[position].id)
                    }

            }
            holder.itemView.setOnClickListener{

        }}

        inner class notesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var textViewdate=itemView.findViewById<TextView>(R.id.DateText)
            var textViewNotes=itemView.findViewById<TextView>(R.id.NotesText)
            var deletebtn=itemView.findViewById<Button>(R.id.deleteitem)
            }


suspend fun deleteNoteById(id: Int) {
    val noteDatabase = NoteDatabase.getInstance(context)
    noteDao = noteDatabase.noteDao()
    noteDao.deleteNoteById(id)
    notes = noteDao.getAllNotes()
    withContext(Dispatchers.Main) {
        notifyDataSetChanged()
    }
}

}


