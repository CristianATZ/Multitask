package net.cristianzvl.multitask.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cristianzvl.multitask.Model.Work
import net.cristianzvl.multitask.Room.Constants
import net.cristianzvl.multitask.Room.NoteDB
import net.cristianzvl.multitask.Room.NotesData


class MultitaskViewModel(applicationContext: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val noteDb = Room.databaseBuilder(applicationContext, NoteDB::class.java, Constants.DB.NAME).allowMainThreadQueries().build()

    init {
        _uiState.value = UiState(
            currentTheme = false,
            notes = noteDb.NotesDao().getAllItems(),
            countNotes = noteDb.NotesDao().getAllItems().size
        )
    }

    fun changeTheme(theme: Boolean){
        _uiState.update { currentState ->
            currentState.copy(currentTheme = theme)
        }
    }

    private fun upCountNotes(){
        _uiState.update { currentState ->
            currentState.copy(countNotes = currentState.countNotes + 1)
        }
    }

    private fun downCountNotes(){
        _uiState.update { currentState ->
            currentState.copy(countNotes = currentState.countNotes - 1)
        }
    }

    fun addNote(
        nota: NotesData
    ){
        /*_uiState.update { currentState ->
            currentState.copy(notes = currentState.notes + nota)
        }*/
        noteDb.NotesDao().insert(nota)
        upCountNotes()
    }

    fun deleteNote(
        nota: NotesData
    ){
        noteDb.NotesDao().delete(nota)
        downCountNotes()
    }

    private fun upCountWorks(){
        _uiState.update { currentState ->
            currentState.copy(countHomeworks = currentState.countHomeworks + 1)
        }
    }

    fun addWork(
        work: Work
    ){
        _uiState.update { currentState ->
            currentState.copy(works = currentState.works + work)
        }

        upCountWorks()
    }
}

