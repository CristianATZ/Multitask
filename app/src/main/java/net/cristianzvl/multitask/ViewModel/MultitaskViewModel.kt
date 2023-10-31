package net.cristianzvl.multitask.ViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cristianzvl.multitask.Model.Note
import net.cristianzvl.multitask.Model.Work


class MultitaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        _uiState.value = UiState(false)
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

    fun addNote(
        nota: Note
    ){
        _uiState.update { currentState ->
            currentState.copy(notes = currentState.notes + nota)
        }

        upCountNotes()
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

