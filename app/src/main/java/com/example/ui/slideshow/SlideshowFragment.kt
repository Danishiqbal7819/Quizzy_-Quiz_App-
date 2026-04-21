package com.example.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.quizAdapter
import com.example.modal_class.Quiz
import com.example.quizzy.R
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowFragment : Fragment() {

    lateinit var adapter: quizAdapter
    private  var quizlist= mutableListOf<Quiz>()
    lateinit var recyclerID: RecyclerView
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        val  view:View= inflater.inflate(R.layout.fragment_slideshow, container, false)
        findId(view)
        populateItems(view)

        setupview(view)
        operations(view)
        return view
    }

    private fun populateItems(view: View) {
        quizlist.add(Quiz("1","Indian Geography"))
        quizlist.add(Quiz("3","Maths Quiz"))
        quizlist.add(Quiz("4","Indian History"))
        quizlist.add(Quiz("5","Java_Programming"))
        quizlist.add(Quiz("6","Social Media"))
        quizlist.add(Quiz("8","Chemistry"))
        quizlist.add(Quiz("10","Basic Physics"))
        quizlist.add(Quiz("11","Android Development"))
        quizlist.add(Quiz("13","World_War"))
        quizlist.add(Quiz("14","Python_Programming"))

    }

    private fun setupview(view: View) {

        adapter= quizAdapter(requireContext(),quizlist)
        recyclerID.layoutManager= GridLayoutManager(requireContext(),2)
        recyclerID.adapter=adapter
    }

    private fun findId(view: View) {
        recyclerID=view.findViewById<RecyclerView>(R.id.recyclerView1)

    }
    private fun operations(view: View) {
    }

}