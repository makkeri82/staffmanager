package com.example.staffmanager.ui.screen.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EventDetailsUiState(val event: Event? = null)

sealed interface EventDetailsAction {
    data object NavigateBack : EventDetailsAction
}

class EventDetailsViewModel(
    private val repository: EventRepository,
    private val selectionState: EventSelectionState
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(event = repository.getEventById(selectionState.selectedEventId.value)) }
        }
    }

    fun onAction(action: EventDetailsAction) {}
}
