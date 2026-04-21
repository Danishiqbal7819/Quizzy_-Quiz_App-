package com.example.modal_class

data class Quiz (
    var id:String="",
    var title:String="",
    var questions:MutableMap<String,Question> = mutableMapOf()
)