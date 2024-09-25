package com.example.anytimememo.domain.model

//ui layer can not directly access data layer
//main access Note layer, Note layer access entity layer
data class Note (
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val date: String = ""
)