package com.example.quizzy

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.modal_class.Question
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ShowquestionsActivity : AppCompatActivity() {
   private lateinit var firestore: FirebaseFirestore
    private var collectionSize : Long = 0
    private  var i=1
    private lateinit var answer:String
    private lateinit var Useranswer:String
    private lateinit var titleOfQuiz:String
     private lateinit var progressBar:ProgressBar
    lateinit var description:TextView
    lateinit var option1:Button
    lateinit var option2:Button
    lateinit var option3:Button
    lateinit var option4:Button
    lateinit var Exitbtn: Button
    lateinit var NextBtn: Button
    lateinit var PreviousBtn: Button
    lateinit var ScoreText:TextView
    lateinit var QuizTitle:TextView
    lateinit var YourScore:TextView
    lateinit var QuesNumber:TextView
    lateinit var Value:String
    private  var Score:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@ShowquestionsActivity, R.color.SkyBlue)
        setContentView(R.layout.activity_showquestions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        description=findViewById(R.id.QuestionId)
        option1=findViewById(R.id.option1)
        option2=findViewById(R.id.option2)
        option3=findViewById(R.id.option3)
        option4=findViewById(R.id.option4)
        Exitbtn=findViewById(R.id.Exit)
        NextBtn=findViewById(R.id.Next)
        ScoreText=findViewById(R.id.scoretext)
        YourScore=findViewById(R.id.YourScore)
        QuizTitle=findViewById(R.id.QuizTitle)
        PreviousBtn=findViewById(R.id.previous)
        QuesNumber=findViewById(R.id.QuestNumber)
        progressBar=findViewById(R.id.progress_circular)


        progressBar.visibility=View.GONE

        val intent = getIntent()
        titleOfQuiz = intent.getStringExtra("Title").toString()
          QuizTitle.text=titleOfQuiz.toString()


          collectionSize(titleOfQuiz)

        Exitbtn.setOnClickListener {
            finish()
        }

        option1.setOnClickListener {
            Useranswer=option1.text.trim().toString()
            SetdisableButton()
            if (getAnswer(Useranswer)){
                option1.setBackgroundResource(R.drawable.buttonbackground)
            }
            else{
                option1.setBackgroundResource(R.drawable.buttonredback)
            }

        }
        option2.setOnClickListener {
            Useranswer=option2.text.trim().toString()
            SetdisableButton()
            if (getAnswer(Useranswer)){
                option2.setBackgroundResource(R.drawable.buttonbackground)
            }
            else{
                option2.setBackgroundResource(R.drawable.buttonredback)
            }

        }
        option3.setOnClickListener {
            Useranswer=option3.text.trim().toString()
            SetdisableButton()
            if (getAnswer(Useranswer)){
                option3.setBackgroundResource(R.drawable.buttonbackground)
            }
            else{
                option3.setBackgroundResource(R.drawable.buttonredback)
            }

        }
        option4.setOnClickListener {
            Useranswer=option4.text.trim().toString()
            SetdisableButton()
            if (getAnswer(Useranswer)){
                option4.setBackgroundResource(R.drawable.buttonbackground)
            }
            else{
                option4.setBackgroundResource(R.drawable.buttonredback)
            }

        }

        PreviousBtn.isClickable=false

        NextBtn.setOnClickListener {
            ScoreText.visibility=View.INVISIBLE
            YourScore.visibility=View.VISIBLE
            SetEnableButton()
            i++
            QuesNumber.text="Q."+i.toString()

            PreviousBtn.isClickable=true
            if (i<=collectionSize!!){

                fetchData(titleOfQuiz,"question"+i)
            }
            else{
                NextBtn.isClickable=false
                YourScore.visibility=View.VISIBLE
                ScoreText.visibility=View.VISIBLE
                YourScore.text="Your_Score"
                ScoreText.text=Score.toInt().toString()+"/"+collectionSize
                Toast.makeText(this,"Quiz Over",Toast.LENGTH_LONG).show()
            }
            PreviousBtn.setOnClickListener {
                SetEnableButton()
                ScoreText.visibility=View.INVISIBLE
                YourScore.visibility=View.VISIBLE
                i--
                QuesNumber.text="Q."+i.toString()
                if (i==0){
                    i=1
                    PreviousBtn.isClickable=false
                }
                else{
                    NextBtn.isClickable=true
                    fetchData(titleOfQuiz,"question"+i)
                }
            }
        }
        }

   private  fun startTimer(minutes: Long, textView: TextView) {
       val duration = minutes.toLong()*60*1000
        val timer = object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                textView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                textView.text = "00:00"
                val builder :AlertDialog.Builder=AlertDialog.Builder(this@ShowquestionsActivity)
                builder.setTitle("Time Over!!")
                builder.setMessage("Your Score is: "+Score+"/"+collectionSize)
                builder.setCancelable(false)
//        builder.setView(R.layout.itemsrecyclerview)

                builder.setIcon(R.drawable.ic4)
                builder.setPositiveButton("Go_Back"){ it, _ ->
                    finish()
                }
                builder.show()
            }
            }
        timer.start()

    }


    private fun SetEnableButton() {
        option1.isClickable=true
        option2.isClickable=true
        option3.isClickable=true
        option4.isClickable=true

        option1.setBackgroundResource(R.drawable.roundshape)
        option2.setBackgroundResource(R.drawable.roundshape)
        option3.setBackgroundResource(R.drawable.roundshape)
        option4.setBackgroundResource(R.drawable.roundshape)

    }
    private fun SetdisableButton() {
        option1.isClickable=false
        option2.isClickable=false
        option3.isClickable=false
        option4.isClickable=false

    }

    private fun getAnswer(Useranswer:String):Boolean {
        var correct=false
        try {
            if(answer.equals(Useranswer)){
                Score = Score+1
//                Toast.makeText(applicationContext,"Correct Answer",Toast.LENGTH_LONG).show()
                ScoreText.visibility=View.VISIBLE
                ScoreText.text= "Correct Answer:$answer"
                correct=true
            }
            else{
                ScoreText.visibility=View.VISIBLE
                Toast.makeText(applicationContext,"Incorrect Answer",Toast.LENGTH_LONG).show()
                ScoreText.text="Correct Answer is:"+answer
            }
        }
        catch (E:Exception){
            Toast.makeText(applicationContext,"Error:"+E,Toast.LENGTH_LONG).show()
        }
   return correct
    }

    private fun collectionSize(questionName: String) {
        firestore=FirebaseFirestore.getInstance()
        firestore.clearPersistence()
        firestore.collection(questionName).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                this.collectionSize = task.result.size().toLong()
                YourScore.visibility=View.VISIBLE
                YourScore.text="Total Question:"+collectionSize.toString()
                showMessage(collectionSize)
                fetchData(titleOfQuiz,"question1")

            } else {
                Toast.makeText(this,"Error in getting size",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun fetchData(titleOfQuiz:String,questionNumber: String) {
        progressBar.visibility=View.VISIBLE
        firestore=FirebaseFirestore.getInstance()
        val DocRef:DocumentReference=firestore.collection(titleOfQuiz).document(questionNumber)
        DocRef.get().addOnSuccessListener {
   if (it.exists()){
       description.text=it.get("description").toString()
       option1.text=it.get("option1").toString()
       option2.text=it.get("option2").toString()
       option3.text=it.get("option3").toString()
       option4.text=it.get("option4").toString()
       answer=it.get("answer").toString()
       progressBar.visibility=View.GONE
   }
            else{
       Toast.makeText(applicationContext,"No data Found",Toast.LENGTH_LONG).show()
       progressBar.visibility=View.GONE
            }
        }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Failed to fetch data",Toast.LENGTH_LONG).show()
                progressBar.visibility=View.GONE
            }
    }

    private fun showMessage(collectionSize:Long){
        val builder :AlertDialog.Builder=AlertDialog.Builder(this@ShowquestionsActivity)
        builder.setTitle("Welcome to Quiz")
        builder.setMessage("Quiz duration is:"+this.collectionSize+"minutes")
        builder.setCancelable(false)
        builder.setIcon(R.drawable.ic4)
        builder.setPositiveButton("Start"){ it, _ ->
            it.cancel()
            startTimer(collectionSize,findViewById(R.id.Timer))
        }
        builder.setNegativeButton("Go_Back"){ _, _ ->
            finish()
        }
        builder.show()
    }
}


