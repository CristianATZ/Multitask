package net.cristianzvl.multitask.ViewModel

import net.cristianzvl.multitask.Model.Note
import net.cristianzvl.multitask.Model.Work

data class UiState (
    val currentTheme: Boolean = false,
    val countNotes: Int = 0,
    val countHomeworks: Int = 0,
    val notes: List<Note> = listOf(),
    val works: List<Work> = listOf()
)