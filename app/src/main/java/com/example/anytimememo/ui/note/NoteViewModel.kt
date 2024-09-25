package com.example.anytimememo.ui.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anytimememo.domain.model.Note
import com.example.anytimememo.domain.repositery.NoteRepository
import com.example.anytimememo.ui.utility.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private fun sendEvent(event: UiEvent){
        viewModelScope.launch {
            _event.send(event)
        }
    }

    init{
        savedStateHandle.get<String>("id")?.let {
            val id = it.toInt()
            viewModelScope.launch {
                repository.getNoteById(id)?.let { note->
                    _state.update { screenState ->
                        screenState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            date = note.date
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.OnContentChange -> {
                _state.update {
                    it.copy(
                        content = event.value
                    )
                }
            }

            is NoteEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.value
                    )
                }
            }

            is NoteEvent.OnDateChange -> {
                _state.update {
                    it.copy(
                        date = event.value
                    )
                }
            }

            NoteEvent.NavigateBack -> sendEvent(UiEvent.NavigateBack)
            NoteEvent.OnSaveNote -> {

                viewModelScope.launch {

                    val state = state.value

                    val note = Note(
                        id = state.id,
                        title = state.title,
                        content = state.content,
                        date = state.date
                    )

                    if (state.id == null) {
                        repository.insertNote(note)
                    } else{
                        repository.updateNote(note)
                    }

                    sendEvent(UiEvent.NavigateBack)
                }
            }

            NoteEvent.DeleteNote ->{
                viewModelScope.launch {

                    val state = state.value
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        content = state.content,
                        date = state.date
                    )

                    repository.deleteNote(note)
                }

                sendEvent(UiEvent.NavigateBack)
            }
        }
    }
}