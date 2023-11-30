package net.cristianzvl.multitask.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.cristianzvl.multitask.Room.Constants
import net.cristianzvl.multitask.Room.NotesData
import net.cristianzvl.multitask.Room.TaskDB
import net.cristianzvl.multitask.Room.WorksData


class MultitaskViewModel(applicationContext: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val db = Room.databaseBuilder(applicationContext, TaskDB::class.java, Constants.DB.NAME).build()

    init {
        viewModelScope.launch(Dispatchers.IO){
            _uiState.value = UiState(
                currentTheme = false,
                notes = db.NotesDao().getAllItems(),
                works = db.WorksDao().getAllItems(),
                countNotes = db.NotesDao().getAllItems().size,
                countHomeworks = db.WorksDao().getAllItems().size
            )
        }
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

    // inicio recompose app -----------------------------------------------------------------
    fun updateAllNotes(){
        _uiState.update { currentState ->
            currentState.copy(notes = db.NotesDao().getAllItems())
        }
    }

    fun updateAllWorks(){
        viewModelScope.launch(Dispatchers.IO){
            _uiState.update { currentState ->
                currentState.copy(works = db.WorksDao().getAllItems())
            }
        }
    }
    // fin recompose app ---------------------------------------------------------------------

    // inicio notas --------------------------------------------------------------------------
    fun addNote(
        nota: NotesData
    ){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().insert(nota)
            updateAllNotes()
        }
    }

    fun updateNote(
        nota: NotesData
    ){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().update(nota)
            updateAllNotes()
        }
    }

    fun deleteNote(
        nota: NotesData
    ){
        viewModelScope.launch(Dispatchers.IO){
            db.NotesDao().delete(nota)
            updateAllNotes()
        }
    }
    // fin notas --------------------------------------------------------------

    // inicio tareas ----------------------------------------------------------
    fun addWork(
        work: WorksData
    ){
        db.WorksDao().insert(work)
        updateAllWorks()
    }

    fun updateWork(
        work: WorksData
    ){
        db.WorksDao().update(work)
        updateAllWorks()
    }

    fun deleteWork(
        work: WorksData
    ){
        db.WorksDao().delete(work)
        updateAllWorks()
    }
    // fin tareas -------------------------------------------------------------
}

