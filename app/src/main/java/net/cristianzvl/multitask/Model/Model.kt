package net.cristianzvl.multitask.Model

import java.util.Date

data class Note(
    val title: String,
    val desc: String,
    val day: String,
    val month: String
)

data class Work(
    val title: String,
    val desc: String,
    //val time: Date
)