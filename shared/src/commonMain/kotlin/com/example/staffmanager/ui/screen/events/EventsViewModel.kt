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

data class EventsUiState(val events: List<Event> = emptyList())

sealed interface EventsAction {
    data class SelectEvent(val eventId: String) : EventsAction
    data object NavigateToEventDetails : EventsAction
}

class EventsViewModel(
    private val eventRepository: EventRepository,
    private val selectionState: EventSelectionState
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(events = eventRepository.getEvents()) }
        }
    }

    fun onAction(action: EventsAction) {
        when (action) {
            is EventsAction.SelectEvent -> selectionState.selectedEventId.value = action.eventId
            EventsAction.NavigateToEventDetails -> Unit
        }
    }
}
