package com.example.anytimememo.ui.utility

sealed interface UiEvent {

    data class Navigate(val route: String): UiEvent
    object NavigateBack: UiEvent

}