package net.cristianzvl.multitask.ViewModel

import net.cristianzvl.multitask.Model.Note
import net.cristianzvl.multitask.Model.Work
import net.cristianzvl.multitask.Room.NotesData

data class UiState (
    val currentTheme: Boolean = false,
    val countNotes: Int = 0,
    val countHomeworks: Int = 0,
    val notes: List<NotesData> = listOf(),
    val works: List<Work> = listOf()
)