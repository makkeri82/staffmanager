package com.example.staffmanager.ui.screen.events

import com.example.staffmanager.ui.components.EventList
import com.example.staffmanager.mockData.mockEvents
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EventsScreen(
    state: EventsUiState,
    // onAction: (EventsAction) -> Unit,
    onEventClick: (String) -> Unit
) {
    EventList(
        events = state.events,
        onEventClick = onEventClick
    )
}

@Composable
@Preview
fun PreviewEventsScreen() {
    MaterialTheme {
        EventsScreen(
            state = EventsUiState(events = mockEvents),
            onEventClick = {}
        )
    }
}
