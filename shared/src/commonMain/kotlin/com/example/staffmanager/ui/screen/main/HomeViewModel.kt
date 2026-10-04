package com.example.staffmanager.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.repository.EventRepository
import com.example.staffmanager.ui.screen.events.EventsAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val events: List<Event> = emptyList(),
    val isAdmin: Boolean = false
)

sealed interface HomeAction {
    data class SelectEvent(val eventId: String) : HomeAction
}

class HomeViewModel(
    private val eventRepository: EventRepository,
    private val selectionState: EventSelectionState
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(events = eventRepository.getAttendedEvents()) }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.SelectEvent -> selectionState.selectedEventId.value = action.eventId
            else -> { }
        }
    }
}
