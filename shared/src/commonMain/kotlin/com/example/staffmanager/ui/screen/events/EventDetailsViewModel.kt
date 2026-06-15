package com.example.staffmanager.ui.screen.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class EventDetailsTab { EVENT, CHAT }
enum class EventDetailsPhase { ACTION, TABS }

data class EventDetailsUiState(
    val event: Event? = null,
    val phase: EventDetailsPhase = EventDetailsPhase.ACTION,
    val activeTab: EventDetailsTab = EventDetailsTab.EVENT,
    val isAdmin: Boolean = false
)

sealed interface EventDetailsAction {
    data object NavigateBack : EventDetailsAction
    data object Accept : EventDetailsAction
    data object Deny : EventDetailsAction
    data class SelectTab(val tab: EventDetailsTab) : EventDetailsAction
}

class EventDetailsViewModel(
    private val repository: EventRepository,
    private val selectionState: EventSelectionState,
    private val sessionState: SessionState
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    event = repository.getEventById(selectionState.selectedEventId.value),
                    isAdmin = sessionState.role == "admin"
                )
            }
        }
    }

    fun onAction(action: EventDetailsAction) {
        when (action) {
            EventDetailsAction.Accept -> {
                _uiState.update {
                    it.copy(
                        phase = EventDetailsPhase.TABS,
                        activeTab = EventDetailsTab.EVENT
                    )
                }
            }

            EventDetailsAction.Deny -> {
                _uiState.update { it.copy(phase = EventDetailsPhase.ACTION) }
            }

            is EventDetailsAction.SelectTab -> {
                _uiState.update { it.copy(activeTab = action.tab) }
            }

            EventDetailsAction.NavigateBack -> {}
        }
    }
}
