package net.cristianzvl.multitask.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

// tabla para notas
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String
)

// tablas para tareas
/*
@Entity(tableName = "works")
data class Work(
    val id: Int = 0,
    val title: String,
    val desc: String
)
*/
