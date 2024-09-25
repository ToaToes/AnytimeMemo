package com.example.anytimememo.ui.note

interface NoteEvent {
    data class OnTitleChange(val value: String): NoteEvent
    data class OnContentChange(val value: String): NoteEvent
    data class OnDateChange(val value: String): NoteEvent
    object OnSaveNote: NoteEvent
    object NavigateBack: NoteEvent
    object DeleteNote: NoteEvent

}