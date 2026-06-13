package com.example.staffmanager.ui.screen.events

import com.example.staffmanager.mockData.mockEvents
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    state: EventDetailsUiState,
    onClickBack: () -> Unit,
) {
        state.event?.let { e ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = e.eventName,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = e.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Date & Time",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "${e.startDate.take(10)}  –  ${e.endDate.take(10)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${e.startTime}  –  ${e.endTime}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Location",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = e.location,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            HorizontalDivider()

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = e.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            Text(
                text = "Participants (${e.participants.size})",
                style = MaterialTheme.typography.titleSmall
            )
            e.participants.forEach { user ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text("${user.firstName} ${user.lastName}") }
                    )
                }
            }

            HorizontalDivider()

            AssistChip(
                onClick = {},
                label = {
                    Text("${e.comments.size} comment${if (e.comments.size != 1) "s" else ""}")
                }
            )
        }
    }
}

@Composable
@Preview
fun PreviewEventDetailsScreen() {
    MaterialTheme {
        EventDetailsScreen(
            state = EventDetailsUiState(event = mockEvents.firstOrNull()),
            onClickBack = {}
        )
    }
}
