package com.example.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.quizAdapter
import com.example.modal_class.Quiz
import com.example.quizzy.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

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

        val  view:View= inflater.inflate(R.layout.fragment_gallery, container, false)
        findId(view)
        populateItems(view)
//        setupFirestore(view)
        setupview(view)
        operations(view)
        return view
    }

    private fun setupFirestore(view: View) {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference =firestore.collection("Quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value==null||error!=null){
                Toast.makeText(requireContext(),"error in fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            Toast.makeText(requireContext(),"Data:"+value.toObjects(Quiz::class.java).toString(),
                Toast.LENGTH_SHORT).show()

            quizlist.clear()
            quizlist.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun populateItems(view: View) {
        quizlist.add(Quiz("1","Football"))
        quizlist.add(Quiz("2","Medium_GK"))
        quizlist.add(Quiz("3","Indian Geography"))
        quizlist.add(Quiz("4","Medium_History"))
        quizlist.add(Quiz("5","Java"))
        quizlist.add(Quiz("6","Social Media"))
        quizlist.add(Quiz("7","Cricket"))
        quizlist.add(Quiz("8","Medium_Chemistry"))
        quizlist.add(Quiz("9","Biology"))
        quizlist.add(Quiz("10","Medium_Physics"))
        quizlist.add(Quiz("11","Android Development"))
        quizlist.add(Quiz("12","Animals"))
        quizlist.add(Quiz("13","Medium_Math"))
        quizlist.add(Quiz("14","Basic Python"))
//        quizlist.add(Quiz("15","12/02/2023"))
//        quizlist.add(Quiz("16","12/02/2023"))

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


