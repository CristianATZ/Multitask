package net.cristianzvl.multitask.ViewModel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cristianzvl.multitask.Model.Work
import net.cristianzvl.multitask.Room.Constants
import net.cristianzvl.multitask.Room.NotesData
import net.cristianzvl.multitask.Room.TaskDB
import net.cristianzvl.multitask.Room.WorksData


class MultitaskViewModel(applicationContext: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val noteDb = Room.databaseBuilder(applicationContext, TaskDB::class.java, Constants.DB.NAME).allowMainThreadQueries().build()
    val workDb = Room.databaseBuilder(applicationContext, TaskDB::class.java, Constants.DB.NAME).allowMainThreadQueries().build()

    init {
        _uiState.value = UiState(
            currentTheme = false,
            notes = noteDb.NotesDao().getAllItems(),
            //works = workDb.WorksDao().getAllItems(), // crashea aqui
            countNotes = noteDb.NotesDao().getAllItems().size,
            //countHomeworks = workDb.WorksDao().getAllItems().size
        )
    }

    fun changeTheme(theme: Boolean){
        _uiState.update { currentState ->
            currentState.copy(currentTheme = theme)
        }
    }

    private fun recomposeView(){
        _uiState.update { currentState ->
            currentState.copy(countNotes = currentState.countNotes + 1)
        }
    }

    // recompose app
    fun getAllNotes(){
        _uiState.update { currentState ->
            currentState.copy(notes = noteDb.NotesDao().getAllItems())
        }
    }

    fun getAllWorks(){
        _uiState.update { currentState ->
            currentState.copy(works = workDb.WorksDao().getAllItems())
        }
    }

    // inicio notas --------------------------------------------------------------------------
    fun addNote(
        nota: NotesData
    ){
        noteDb.NotesDao().insert(nota)
        getAllNotes()
    }

    fun updateNote(
        nota: NotesData
    ){
        noteDb.NotesDao().update(nota)
        getAllNotes()
    }

    fun deleteNote(
        nota: NotesData
    ){
        noteDb.NotesDao().delete(nota)
        getAllNotes()
    }
    // fin notas --------------------------------------------------------------

    // inicio tareas ----------------------------------------------------------
    fun addWork(
        work: WorksData
    ){
        workDb.WorksDao().insert(work)
        getAllWorks()
    }

    fun updateWork(
        work: WorksData
    ){
        workDb.WorksDao().update(work)
        getAllWorks()
    }

    fun deleteWork(
        work: WorksData
    ){
        workDb.WorksDao().delete(work)
        getAllWorks()
    }
    // fin tareas -------------------------------------------------------------
}

