package com.example.staffmanager.ui.screen.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import com.example.staffmanager.ui.components.EventList
import com.example.staffmanager.mockData.mockEventsUpcoming
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staffmanager.theme.EventAppTheme

@Composable
fun EventsScreen(
    state: EventsUiState,
    // onAction: (EventsAction) -> Unit,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            modifier = modifier.padding(10.dp),
            text = "Upcoming",
            style = MaterialTheme.typography.titleLarge
        )
        EventList(
            events = state.events,
            onEventClick = onEventClick
        )
    }

}

@Composable
@Preview
fun PreviewEventsScreen() {
    EventAppTheme {
        EventsScreen(
            state = EventsUiState(events = mockEventsUpcoming),
            onEventClick = {}
        )
    }
}
