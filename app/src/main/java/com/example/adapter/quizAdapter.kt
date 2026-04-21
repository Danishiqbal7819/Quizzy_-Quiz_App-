package com.example.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.modal_class.Quiz
import com.example.quizzy.R
import com.example.quizzy.ShowquestionsActivity
import com.example.utils.colorpicker
import com.example.utils.iconpicker

class quizAdapter(val context: Context, val quizzes: List<Quiz>):RecyclerView.Adapter<quizAdapter.quizViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): quizViewHolder {
       val view=LayoutInflater.from(context).inflate(R.layout.itemsrecyclerview,parent,false)
        return quizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  quizzes.size
    }
    override fun onBindViewHolder(holder: quizViewHolder, position: Int) {
      holder.textViewTitle.text=quizzes[position].title
        holder.cardComtainer.setCardBackgroundColor(Color.parseColor(colorpicker.getcolor()))
        holder.Iconview.setImageResource(iconpicker.getIcon())

        holder.itemView.setOnClickListener{
            var Title=holder.textViewTitle.text.toString().trim()
            val intent=Intent(context,ShowquestionsActivity::class.java)
            intent.putExtra("Title",Title)
            context.startActivity(intent)
        }

    }
    inner class quizViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textViewTitle=itemView.findViewById<TextView>(R.id.quizTitle)
        var Iconview=itemView.findViewById<ImageView>(R.id.quizIcon)
        var cardComtainer=itemView.findViewById<CardView>(R.id.cardConatainer)
    }
}