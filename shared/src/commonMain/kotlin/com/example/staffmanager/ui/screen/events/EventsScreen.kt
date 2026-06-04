package com.example.staffmanager.ui.screen.events

import com.example.staffmanager.mockData.mockEvents
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EventsScreen(
    state: EventsUiState,
    onAction: (EventsAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAction(EventsAction.SelectEvent(event.id))
                        onAction(EventsAction.NavigateToEventDetails)
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = event.eventName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewEventsScreen() {
    MaterialTheme {
        EventsScreen(
            state = EventsUiState(events = mockEvents),
            onAction = {}
        )
    }
}
