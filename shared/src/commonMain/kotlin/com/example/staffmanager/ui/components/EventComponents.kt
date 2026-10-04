package com.example.staffmanager.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.mockData.mockEventsUpcoming
import com.example.staffmanager.theme.EventAppTheme

@Composable
fun EventList(
    events: List<Event>,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        items(events) { event ->
            EventCard(
                event = event,
                onEventClick = onEventClick
            )
        }
    }
}

@Preview
@Composable
fun PreviewEventList() {
    EventAppTheme {
        EventList(
            events = mockEventsUpcoming,
            onEventClick = {}
        )
    }
}
