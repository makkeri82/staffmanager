package com.example.staffmanager.ui.screen.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Event
import com.example.staffmanager.ui.screen.chat.ChatAction
import com.example.staffmanager.ui.screen.chat.ChatScreen
import com.example.staffmanager.ui.screen.chat.ChatUiState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staffmanager.mockData.mockEvents

@Composable
fun EventDetailsScreen(
    state: EventDetailsUiState,
    chatState: ChatUiState,
    onAction: (EventDetailsAction) -> Unit,
    onChatAction: (ChatAction) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            when (state.activeTab) {
                EventDetailsTab.EVENT -> EventDetailsContent(state)
                EventDetailsTab.CHAT -> ChatScreen(chatState, onChatAction)
            }
        }
        when (state.phase) {
            EventDetailsPhase.ACTION -> EventDetailsActionBar(onAction)
            EventDetailsPhase.TABS -> EventDetailsTabBar(state.activeTab, onAction)
        }
    }
}

@Composable
private fun EventDetailsContent(state: EventDetailsUiState) {
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

            if (state.isAdmin) {
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
            }

//            AssistChip(
//                onClick = {},
//                label = {
//                    Text("${e.comments.size} comment${if (e.comments.size != 1) "s" else ""}")
//                }
//            )
        }
    }
}

@Composable
private fun EventDetailsActionBar(onAction: (EventDetailsAction) -> Unit) {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onAction(EventDetailsAction.Accept) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) { Text("Accept") }

            OutlinedButton(
                onClick = { onAction(EventDetailsAction.Deny) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
            ) { Text("Deny") }
        }
    }
}

@Composable
private fun EventDetailsTabBar(
    activeTab: EventDetailsTab,
    onAction: (EventDetailsAction) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = activeTab == EventDetailsTab.EVENT,
            onClick = { onAction(EventDetailsAction.SelectTab(EventDetailsTab.EVENT)) },
            icon = { Icon(Icons.Default.Event, contentDescription = "Event") },
            label = { Text("Event") }
        )
        NavigationBarItem(
            selected = activeTab == EventDetailsTab.CHAT,
            onClick = { onAction(EventDetailsAction.SelectTab(EventDetailsTab.CHAT)) },
            icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Chat") },
            label = { Text("Chat") }
        )
    }
}

@Composable
@Preview
fun PreviewEventDetailsScreen() {
    MaterialTheme {
        EventDetailsScreen(
            state = EventDetailsUiState(event = mockEvents.firstOrNull()),
            chatState = ChatUiState(),
            onAction = {},
            onChatAction = {}
        )
    }
}
