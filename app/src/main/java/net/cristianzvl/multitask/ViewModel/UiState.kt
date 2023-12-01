package net.cristianzvl.multitask.ViewModel

import net.cristianzvl.multitask.Room.NotesData
import net.cristianzvl.multitask.Room.WorksData

data class UiState (
    val currentTheme: Boolean = false,
    val countNotes: Int = 0,
    val countHomeworks: Int = 0,
    val currentTask: Int = 0,
    val notes: List<NotesData> = listOf(),
    val works: List<WorksData> = listOf()
)