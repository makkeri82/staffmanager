package com.example.staffmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staffmanager.mockData.Event
import com.example.staffmanager.theme.EventAppTheme
import com.example.staffmanager.mockData.mockEventsUpcoming
import com.example.staffmanager.util.toDayMonth

@Composable
fun EventCard(
    event: Event,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier.fillMaxWidth()
            .clickable {
                onEventClick(event.id)
            }
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.padding(4.dp)) {
            Text(
                text = event.eventName,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp
            )
            Text(
                text = event.venueName,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = modifier.padding(4.dp)
        ) {
            Text(
                text = event.startDate.toDayMonth(),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewEventCard() {
    EventAppTheme {
        EventCard(
            event = mockEventsUpcoming.first(),
            onEventClick = {})
    }
}